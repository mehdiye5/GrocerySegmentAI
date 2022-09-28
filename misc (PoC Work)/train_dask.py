import numpy as np
import pandas as pd
import matplotlib.pyplot as plt
import sklearn
import xgboost as xgb

import dask.dataframe as dd
from dask_ml.model_selection import train_test_split
import dask.array as da
import dask.distributed

import os
import warnings
import datetime as dt
import json
import boto3
from io import StringIO


pd.options.mode.chained_assignment = None  # default='warn'
warnings.simplefilter(action='ignore', category=FutureWarning)

def main(orders, products, all_order_products):
    """
    Given an orders.csv, products.csv, and all_order_products.csv trains an XGBoost model
    to predict which users will purchase what products on their next order

    Parameters
    -------
    orders_fpath: dataframe

    products_fpath: dataframe

    all_products_orders_fpath: dataframe

    Returns
    -------
    predicted_carts.csv
        A CSV file predicting the products that users will buy next

    model.txt
        A saved XGB model that can be used to make predictions on new data.
    """

    print("---------- Reading Data ----------")
    # Data variables
    missing_value_formats = ["n.a.","?","NA","n/a", "na", "--","-"]

    print("---------- Preprocessing Data ----------")
    # Creating Timestamp
    orders['timestamp'] =  pd.to_datetime(orders['timestamp'])
    orders = orders.sort_values(["user_id", "timestamp"])

    orders["order_number"] = orders.groupby('user_id').cumcount()
    orders["order_dow"] = orders["timestamp"].dt.dayofweek
    orders["order_hour_of_day"] = orders["timestamp"].dt.hour
    orders['days_since_prior_order'] = (orders["timestamp"] - orders.groupby('user_id')['timestamp'].shift(1)).dt.round('1d').dt.days
    orders['days_since_prior_order'].fillna(0.0, inplace=True)
    orders = orders.drop(columns=["timestamp"])
    orders.head()

    # Creating 'reordered' column
    all_order_products = pd.merge(all_order_products, orders[["order_id","user_id","order_number"]], on="order_id")
    all_order_products = all_order_products.sort_values(["user_id","order_number"])
    all_order_products['reordered'] = all_order_products.duplicated(subset=['user_id','product_id'])

    # Creating Training and Prior Sets
    order_products_prior = all_order_products[all_order_products.groupby(['user_id'])['order_number'].transform(max) != all_order_products['order_number']]
    order_products_train = all_order_products[all_order_products.groupby(['user_id'])['order_number'].transform(max) == all_order_products['order_number']]
    del all_order_products

    # Creating Dask Dataframes
    products = dd.from_pandas(products, npartitions=4)
    orders = dd.from_pandas(orders, npartitions=4)
    order_products_prior = dd.from_pandas(order_products_prior, npartitions=4)
    order_products_train = dd.from_pandas(order_products_train, npartitions=4)

    # Set all_data to false if testing. 
    # Using all the data takes a long time to train
    all_data = False
    if not all_data:
        ids = orders["user_id"].unique()[:100].compute()
        orders = orders[orders["user_id"].isin(ids)]

    #creating a dataframe that will contain only prior information
    op = dd.merge(orders, order_products_prior, on='order_id', how='inner', suffixes=('', '_y'))
    op = op.drop([x for x in op.columns if x.endswith("_y")], axis=1)

    #Total number of orders placed by each users
    users = op.groupby(by='user_id')['order_number'].aggregate('max').to_frame('u_num_of_orders').reset_index()

    #average number of products bought by the user in each purchase.

    #1. First getting the total number of products in each order.
    total_prd_per_order = op.groupby(by=['user_id', 'order_id'])['product_id'].aggregate('count').to_frame('total_products_per_order').reset_index()

    #2. Getting the average products purchased by each user
    avg_products = total_prd_per_order.groupby(by=['user_id'])['total_products_per_order'].mean().to_frame('u_avg_prd').reset_index()

    #deleting the total_prd_per_order dataframe
    del total_prd_per_order

    #dow (Day of week) of most orders placed by each user 
    dow = op.groupby('user_id')['order_dow'].agg('mean').to_frame('dow_most_orders_u').reset_index()

    #hour of day when most orders placed by each user
    hod = op.groupby('user_id')['order_hour_of_day'].agg('mean').to_frame('hod_most_orders_u').reset_index()

    # Merging the user created features.
    #1. merging avg_products with users
    users = users.merge(avg_products, on='user_id', how='left')
    #deleting avg_products
    del avg_products

    #2. merging dow with users.
    users = users.merge(dow, on='user_id', how='left')
    #deleting dow
    del dow

    #3. merging hod with users
    users = users.merge(hod, on='user_id', how='left')
    #deleting dow
    del hod

    #number of time a product was purchased.
    prd = op.groupby('product_id')['order_id'].agg('count').to_frame('prd_count_p').reset_index()

    #products reorder ratio.
    reorder_p = op.groupby(by='product_id')['reordered'].agg('mean').to_frame('p_reordered_ratio').reset_index()

    #merging the reorder_p with prd
    prd = prd.merge(reorder_p, on='product_id', how='left')
    #deleting reorder_p
    del reorder_p

    #how many times a user bought the same product.
    uxp = op.groupby(by=['user_id', 'product_id'])['order_id'].agg('count').to_frame('uxp_times_bought').reset_index()

    #reorder ratio of the user for each product.
    reorder_uxp = op.groupby(by=['user_id', 'product_id'])['reordered'].agg('mean').to_frame('uxp_reordered_ratio').reset_index()

    #merging the two dataframes into one
    uxp = uxp.merge(reorder_uxp, on=['user_id', 'product_id'], how='left')
    #deleting reorder_uxp
    del reorder_uxp

    #merging users df into uxp
    data = uxp.merge(users, on='user_id', how='left')

    #merging products df into data
    data = data.merge(prd, on='product_id', how='left')
    data = data.merge(order_products_train[["user_id", "order_id"]].drop_duplicates(), on='user_id')

    #deleting unwanted dfs
    del [users, prd, uxp]

    #merging the information from the order_proucts_train df into the data_train.
    data_train = data.merge(order_products_train[['product_id', 'order_id', 'reordered']], on=['product_id', 'order_id'], how='left')
    del data

    #merging the aisles and department ids to with the train and test data
    data_train = data_train.merge(products[['product_id', 'aisle_id', 'department_id']], on='product_id', how='left')

    #setting user_id and product_id as index. (Not Supported in Dask)
    # data_train = data_train.set_index(['user_id', 'product_id'])
    data_train = data_train.reset_index(drop=True).fillna(0)

    #creating data and labels
    X_train, y_train = data_train[[x for x in data_train.columns if x not in ["reordered","user_id","product_id"]]], data_train['reordered']

    print("---------- Training Model ----------")
    #setting boosters parameters
    parameters = {
        'eval_metric' : 'logloss',
        'max_depth' : 5,
        'colsample_bytree' : 0.4,
        'subsample' : 0.8
    }

    # instantiating the model
    xgb_clf = xgb.dask.DaskXGBClassifier(parameters=parameters, num_boost_round=10) #change to 10

    # training model
    model = xgb_clf.fit(X_train, y_train)
    model.save_model("model.txt")

    # setting a threshold.?
    y_pred = (xgb_clf.predict_proba(X_train)[:, 1] >= 0.21).astype('int')

    # saving the prediction as a new column in data_test
    data_train['prediction'] = y_pred

    # Training F1_score
    print('Training F1 score: ', sklearn.metrics.f1_score(data_train['reordered'].astype('int').compute(), data_train['prediction'].astype('int').compute()))

    print("---------- Writing Predicitions to Disk ----------")
    # Writing DF to disk for later eval
    with warnings.catch_warnings():
        warnings.simplefilter('ignore')
        #writing validation file to disk
        data_train[["user_id","product_id","prediction"]].reset_index().to_csv("predicted_carts.csv", single_file=True, index=False)

def read_df(df_json):
    bucket_name = df_json['s3Bucket']
    object_name = df_json['s3Object']
    s3 = boto3.resource('s3')
    s3_obj = s3.Bucket(bucket_name).Object(object_name).get()
    body = s3_obj['Body']
    csv_string = body.read().decode('utf-8')
    df = pd.read_csv(StringIO(csv_string))


    if df_json['type'] == 'Orders':
        orders_cols = []
        orders_user_id = df_json['userId']
        orders_order_id = df_json['orderId']
        orders_timestamp = df_json['timestamp']
        orders_cols.append(orders_user_id)
        orders_cols.append(orders_order_id)
        orders_cols.append(orders_timestamp)
        # orders_featureColumns = df_json['featureColumns']
        # for f in orders_featureColumns:
        #     orders_cols.append(f['columnName'])
        return df[orders_cols]

    if df_json['type'] == 'Products':
        products_cols = []
        products_product_id = df_json['productId']
        products_cols.append(products_product_id)
        products_featureColumns = df_json['featureColumns']
        for f in products_featureColumns:
            products_cols.append(f['columnName'])
        return df[products_cols]

    if df_json['type'] == 'Order_products':
        order_products_cols = []
        order_products_order_id = df_json['orderId']
        order_products_product_id = df_json['productId']
        order_products_cols.append(order_products_order_id)
        order_products_cols.append(order_products_product_id)
        order_products_featureColumns = df_json['featureColumns']
        for f in order_products_featureColumns:
            order_products_cols.append(f['columnName'])
        return df[order_products_cols]
        
def return_df(message):
    data = message

    orders_list = []
    products_list = []
    order_products_list = []

    # keep tenant_id for this data json
    tenant_id = data[0]['tenantId']

    for df_json in data:
        if df_json['type'] == 'Orders':
            orders_list.append(read_df(df_json))
        elif df_json['type'] == 'Products':
            products_list.append(read_df(df_json))
        elif df_json['type'] == 'Order_products':
            order_products_list.append(read_df(df_json))
        else:
            print('Wrong type!')
        
    if len(orders_list) > 1:
        orders = pd.concat(orders_list).reset_index().drop('index', axis=1)
    else:
        orders = orders_list[0]

    if len(products_list) > 1:
        products = pd.concat(products_list).reset_index().drop('index', axis=1)
    else:
        products = products_list[0]

    if len(order_products_list) > 1:
        order_products = pd.concat(
            order_products_list).reset_index().drop('index', axis=1)
    else:
        order_products = order_products_list[0]

    return orders, products, order_products, tenant_id

if __name__ == "__main__":

    # Initiating Dask Cluster
    cluster = dask.distributed.LocalCluster()
    client = dask.distributed.Client(
        n_workers=4, 
        threads_per_worker=1,
        memory_limit='16GB'
        )

    try:
        orders, products, order_products, tenant_id = return_df('data.json')
        main(orders, products, order_products)
    except:
        print("FAILED")