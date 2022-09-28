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

pd.options.mode.chained_assignment = None  # default='warn'
warnings.simplefilter(action='ignore', category=FutureWarning)

def main(orders_fpath, products_fpath, all_products_orders_fpath, model_fpath):
    """
    Given an orders.csv, products.csv, and all_order_products.csv uses a
    pre trained XGBoost model to predict which users will purchase what 
    products on their next order.

    Parameters
    -------
    orders_fpath: string
        path to orders.csv file
    
    products_fpath: string
        path to products.csv file

    all_products_orders_fpath: string
        path to all_order_products.csv file

    model_fpath: string
        path to saved xgboost model

    Returns
    -------
    predicted_carts.csv
        A CSV file predicting the products that users will buy next
    """

    print("---------- Reading Data ----------")
    # Data variables
    PATH = "./archive/"
    missing_value_formats = ["n.a.","?","NA","n/a", "na", "--","-"]

    products = pd.read_csv(products_fpath, na_values = missing_value_formats)
    orders = pd.read_csv(orders_fpath, na_values = missing_value_formats)
    all_order_products = pd.read_csv(all_products_orders_fpath, na_values = missing_value_formats)

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

    print("---------- Loading Model ----------")
    # instantiating the model
    xgb_clf = xgb.dask.DaskXGBClassifier()
    xgb_clf.load_model(model_fpath)

    # setting a threshold.?
    y_pred = (xgb_clf.predict_proba(X_train)[:, 1] >= 0.21).astype('int')

    # saving the prediction as a new column in data_test
    data_train['prediction'] = y_pred

    print("---------- Writing Predicitions to Disk ----------")
    # Writing DF to disk for later eval
    with warnings.catch_warnings():
        warnings.simplefilter('ignore')
        #writing validation file to disk
        data_train[["user_id","product_id","prediction"]].reset_index().to_csv("predicted_carts.csv", single_file=True, index=False)

if __name__ == "__main__":

    # Initiating Dask Cluster
    cluster = dask.distributed.LocalCluster()
    client = dask.distributed.Client(
        n_workers=4, 
        threads_per_worker=1,
        memory_limit='16GB'
        )
    
    orders_fpath = "./archive/orders.csv"
    products_fpath = "./archive/products.csv"
    all_products_orders_fpath = "./archive/all_order_products.csv"
    model_fpath = "./model.txt"

    main(orders_fpath, products_fpath, all_products_orders_fpath, model_fpath)