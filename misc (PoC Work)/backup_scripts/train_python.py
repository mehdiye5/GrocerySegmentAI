import numpy as np
import pandas as pd
import xgboost as xgb
import lightgbm as lgb
import matplotlib.pyplot as plt
import sklearn
import scipy

import os
import gc
import warnings
import datetime as dt

pd.options.mode.chained_assignment = None  # default='warn'

# Data variables
PATH = "./simplifiedinstacartdata/"
missing_value_formats = ["n.a.","?","NA","n/a", "na", "--","-"]

for file in os.listdir(PATH):
    dfname = file.split('.')[0].replace("__","_")
    globals()[dfname] = pd.read_csv(PATH + file, na_values = missing_value_formats)
    print(dfname)

# ---------- Data preprocessing ---------- 

# Creating Features on Timestamp
orders['timestamp'] =  pd.to_datetime(orders['timestamp'])
orders = orders.sort_values(["user_id", "timestamp"])

orders["order_number"] = orders.groupby('user_id').cumcount()
orders["order_dow"] = orders["timestamp"].dt.dayofweek
orders["order_hour_of_day"] = orders["timestamp"].dt.hour
orders['days_since_prior_order'] = (orders["timestamp"] - orders.groupby('user_id')['timestamp'].shift(1)).dt.round('1d').dt.days
orders['days_since_prior_order'].fillna(0.0, inplace=True)
orders = orders.drop(columns=["timestamp"])

# Creating Prior + Train Set
all_order_products = pd.merge(all_order_products, orders[["order_id","user_id","order_number"]], on="order_id")

order_products_prior = all_order_products[all_order_products.groupby(['user_id'])['order_number'].transform(max) != all_order_products['order_number']]
order_products_train = all_order_products[all_order_products.groupby(['user_id'])['order_number'].transform(max) == all_order_products['order_number']]
del all_order_products

# creating a dataframe that will contain only prior information
op = pd.merge(orders, order_products_prior, on='order_id', how='inner', suffixes=('', '_y'))
op = op.drop(op.filter(regex='_y$').columns.tolist(), axis=1)

# ---------- Creating User Features ----------

# Total number of orders placed by each users
users = op.groupby(by='user_id')['order_number'].aggregate('max').to_frame('u_num_of_orders').reset_index()

# average number of products bought by the user in each purchase.

#1. First getting the total number of products in each order.
total_prd_per_order = op.groupby(by=['user_id', 'order_id'])['product_id'].aggregate('count').to_frame('total_products_per_order').reset_index()

#2. Getting the average products purchased by each user
avg_products = total_prd_per_order.groupby(by=['user_id'])['total_products_per_order'].mean().to_frame('u_avg_prd').reset_index()
del total_prd_per_order

# Dow (Day of week) of most orders placed by each user 
dow = op.groupby('user_id')['order_dow'].agg(lambda x: x.mode().iloc[0]).to_frame(name='dow_most_orders_u').reset_index()

# hour of day when most orders placed by each user
hod = op.groupby('user_id')['order_hour_of_day'].agg(lambda x: x.mode().iloc[0]).to_frame(name='hod_most_orders_u').reset_index()

# Merging the user created features.

#1. merging avg_products with users
users = users.merge(avg_products, on='user_id', how='left')
del avg_products

#2. merging dow with users.
users = users.merge(dow, on='user_id', how='left')
del dow

#3. merging hod with users
users = users.merge(hod, on='user_id', how='left')
del hod

# ---------- Creating Product Features ----------

#number of time a product was purchased.
prd = op.groupby(by='product_id')['order_id'].agg('count').to_frame('prd_count_p').reset_index()

#products reorder ratio.
reorder_p = op.groupby(by='product_id')['reordered'].agg('mean').to_frame('p_reordered_ratio').reset_index()

#merging the reorder_p with prd
prd = prd.merge(reorder_p, on='product_id', how='left')
del reorder_p

# ---------- Creating User-Product Features ----------

#how many times a user bought the same product.
uxp = op.groupby(by=['user_id', 'product_id'])['order_id'].agg('count').to_frame('uxp_times_bought').reset_index()

#reorder ratio of the user for each product.
reorder_uxp = op.groupby(by=['user_id', 'product_id'])['reordered'].agg('mean').to_frame('uxp_reordered_ratio').reset_index()

#merging the two dataframes into one
uxp = uxp.merge(reorder_uxp, on=['user_id', 'product_id'], how='left')
del reorder_uxp

# ---------- Merging All Features into one DF ----------

#merging users df into uxp
data = uxp.merge(users, on='user_id', how='left')

#merging products df into data
data = data.merge(prd, on='product_id', how='left')
data = data.merge(order_products_train[["user_id", "order_id"]].drop_duplicates(), on='user_id')

#deleting unwanted dfs
del [users, prd, uxp]

# ---------- Creating Training + Validation ----------

#keeping only the train eval set from the orders dataframe.
order_future = orders.sort_values("order_number", ascending=False).drop_duplicates(subset=["user_id"])[['user_id','order_id']]

#merging the information from the order_proucts_train df into the data_train.
data_train = data.merge(order_products_train[['product_id', 'order_id', 'reordered']], on=['product_id', 'order_id'], how='left')
del data

#filling the NAN values (Target label only)
data_train['reordered'].fillna(0.0, inplace=True)
data_train['reordered'] = data_train['reordered'].astype(int)

#deleting eval_set, order_id as they are not needed for training.
data_train.drop(['order_id'], axis=1, inplace=True)

#deleting unwanted df
del [order_products_prior, order_products_train, orders, order_future]

#merging the aisles and department ids to with the train and test data
data_train = data_train.merge(products[['product_id', 'aisle_id', 'department_id']], on='product_id', how='left')

#setting user_id and product_id as index.
data_train = data_train.set_index(['user_id', 'product_id'])

#creating training and validation set
train, valid = sklearn.model_selection.train_test_split(data_train, test_size=0.1)
del data_train

# ---------- Building XGB Model ----------

#creating data and labels
X_train, y_train = train.drop('reordered', axis=1), train['reordered']

#setting boosters parameters
parameters = {
    'eval_metric' : 'logloss',
    'max_depth' : 5,
    'colsample_bytree' : 0.4,
    'subsample' : 0.8
}

#instantiating the model
xgb_clf = xgb.XGBClassifier(objective='binary:logistic', parameters=parameters, num_boost_round=10) #change to 10

#training model
model = xgb_clf.fit(X_train, y_train)

#feature importance plot
xgb.plot_importance(model)

# ---------- Making Validation Predictions ----------

#creating data and labels
X_valid, y_valid = valid.drop('reordered', axis=1), train['reordered']

#setting a threshold.?
y_pred = (xgb_clf.predict_proba(X_valid)[:, 1] >= 0.21).astype('int')

#saving the prediction as a new column in data_test
valid['prediction'] = y_pred
valid.head()

#valid f1_score
print('F1 score: ', sklearn.metrics.f1_score(valid['reordered'], valid['prediction']))

# Writing DF to disk for later eval
with warnings.catch_warnings():
    warnings.simplefilter('ignore')

    #writing validation file to disk
    valid.reset_index().to_csv("validation.csv")