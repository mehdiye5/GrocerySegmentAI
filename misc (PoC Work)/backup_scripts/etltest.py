import sys
import numpy as np
import pandas as pd
import xgboost as xgb
import sklearn
import scipy

import zipfile
import os
import gc
import warnings

pd.options.mode.chained_assignment = None  # default='warn'


assert sys.version_info >= (3, 5) # make sure we have Python 3.5+

from pyspark.sql import SparkSession, functions, types #type:ignore

def main():

    # Data variables + Schema
    PATH = "archive"

    products_schema = types.StructType([
        types.StructField('product_id', types.IntegerType(), True),
        types.StructField('product_name', types.StringType(), True),
        types.StructField('aisle_id', types.IntegerType(), True),
        types.StructField('department_id', types.IntegerType(), True),
    ])

    order_products_train_schema = types.StructType([
        types.StructField('order_id', types.IntegerType(), True),
        types.StructField('product_id', types.IntegerType(), True),
        types.StructField('add_to_cart_order', types.IntegerType(), True),
        types.StructField('reordered', types.IntegerType(), True),
    ])

    orders_schema = types.StructType([
        types.StructField('order_id', types.IntegerType(), True),
        types.StructField('user_id', types.IntegerType(), True),
        types.StructField('eval_set', types.StringType(), True),
        types.StructField('order_number', types.IntegerType(), True),
        types.StructField('order_dow', types.IntegerType(), True),
        types.StructField('order_hour_of_day', types.IntegerType(), True),
        types.StructField('days_since_prior_order', types.FloatType(), True),
    ])

    order_products_prior_schema = types.StructType([
        types.StructField('order_id', types.IntegerType(), True),
        types.StructField('product_id', types.IntegerType(), True),
        types.StructField('add_to_cart_order', types.IntegerType(), True),
        types.StructField('reordered', types.IntegerType(), True),
    ])

    aisles_schema = types.StructType([
        types.StructField('aisle_id', types.IntegerType(), True),
        types.StructField('aisle', types.StringType(), True)
    ])

    departments_schema = types.StructType([
        types.StructField('department_id', types.IntegerType(), True),
        types.StructField('department', types.StringType(), True)
    ])

    # Load dataframes
    products = spark.read.csv('/Users/brendanartley/dev/733/final_project/archive/products.csv', schema=products_schema, header=True)
    order_products_train = spark.read.csv('/Users/brendanartley/dev/733/final_project/archive/order_products__train.csv', schema=order_products_train_schema, header=True)
    orders = spark.read.csv('/Users/brendanartley/dev/733/final_project/archive/orders.csv', schema=orders_schema, header=True)
    order_products_prior = spark.read.csv('/Users/brendanartley/dev/733/final_project/archive/order_products__prior.csv', schema=order_products_prior_schema, header=True)
    aisles = spark.read.csv('/Users/brendanartley/dev/733/final_project/archive/aisles.csv', schema=aisles_schema, header=True)
    departments = spark.read.csv('/Users/brendanartley/dev/733/final_project/archive/departments.csv', schema=departments_schema, header=True)

    # Filtering IDs
    ids = [x[0] for x in orders.select('user_id').distinct().limit(100).collect()]
    orders = orders.filter(orders['user_id'].isin(ids))

    # Creating a dataframe that will contain only prior information
    op = orders.join(order_products_prior, how='inner', on='order_id')

    # ---------- USER FEATURES ----------

    #Total number of orders placed by each users
    users = op.groupBy('user_id').agg(functions.max('order_number').alias("u_num_of_orders"))

    #average number of products bought by the user in each purchase.
    #1. First getting the total number of products in each order.
    total_prd_per_order = op.groupBy('user_id', 'order_id').agg(functions.count('product_id').alias('total_products_per_order'))

    #2. Getting the average products purchased by each user
    avg_products = total_prd_per_order.groupBy('user_id').agg(functions.mean('total_products_per_order').alias('u_avg_prd'))

    #dow (Day of week) of most orders placed by each user 
    dow = op.groupBy('user_id').agg(functions.mean('order_dow').alias('dow_most_orders_u'))

    #hour of day when most orders placed by each user
    hod = op.groupBy('user_id').agg(functions.mean('order_hour_of_day').alias('hod_most_orders_u'))

    # Merging the user created features.

    #1. merging avg_products with users
    users = users.join(avg_products, on='user_id', how='left')

    #2. merging dow with users.
    users = users.join(dow, on='user_id', how='left')

    #3. merging hod with users
    users = users.join(hod, on='user_id', how='left').cache()

    # ---------- PRODUCT FEATURES ----------

    #number of time a product was purchased.
    prd = op.groupBy('product_id').agg(functions.count('order_id').alias('prd_count_p'))

    #products reorder ratio.
    reorder_p = op.groupBy('product_id').agg(functions.mean('reordered').alias('p_reordered_ratio'))

    #merging the reorder_p with prd
    prd = prd.join(reorder_p, on='product_id', how='left')

    # ---------- USER-PRODUCT FEATURES ----------

    #how many times a user bought the same product.
    uxp = op.groupBy('user_id', 'product_id').agg(functions.count('order_id').alias('uxp_times_bought'))

    #reorder ratio of the user for each product.
    reorder_uxp = op.groupBy('user_id', 'product_id').agg(functions.mean('reordered').alias('uxp_reordered_ratio'))

    #merging the two dataframes into one
    uxp = uxp.join(reorder_uxp, on=((uxp["user_id"]==reorder_uxp["user_id"]) & (uxp["product_id"]==reorder_uxp["product_id"])), how='left')
    uxp.show(5)
    assert 1>2

    # ---------- Merging All DF's ----------

    #merging users df into uxp
    data = uxp.join(users, on='user_id', how='left')

    #merging products df into data
    data = data.join(prd, on='product_id', how='left').cache()

    # ---------- Creating Training + Validation Sets ----------
    # keeping only the train eval set from the orders dataframe.
    order_future = orders.where(orders["eval_set"] == 'train').select(orders["user_id"], orders["eval_set"], orders["order_id"])

    #merging the order_future with the data.
    data = data.join(order_future, on='user_id', how='left')

    #preparing the train df.
    data_train = data.where(data['eval_set'] == 'train')

    print("here")
    #order_products_train.show(5)
    data_train.show(5)

    #merging the information from the order_proucts_train df into the data_train.
    data_train = (data_train.join(order_products_train.select('product_id', 'order_id', 'reordered'), 
                    on=((order_products_train['product_id']==data_train['product_id']) & (order_products_train['order_id']==data_train['order_id'])), 
                     how='left')).cache()
    data_train.show(5)
    print("here2")

    #filling null + selecting subset
    data_train = data_train.fillna(0, subset=['reordered']).select('user_id','product_id','uxp_times_bought','uxp_reordered_ratio','u_num_of_orders','u_avg_prd','dow_most_orders_u','hod_most_orders_u','prd_count_p','p_reordered_ratio','reordered')
    data_train.show(5)
    print("here3")

    # merging the aisles and department ids to with the train and test data
    data_train = data_train.join(products.select('product_id', 'aisle_id'), on='product_id', how='left')
    data_train.show(5)
    print("here4")

    # department
    data_train = data_train.join(products.select('product_id', 'department_id'), on='product_id', how='left')
    data_train.show(5)
    print("here4")

    train, test = df4.randomSplit(weights=[0.9, 0.1], seed=24)
    print("done")

if __name__ == '__main__':
    spark = SparkSession.builder.appName('example code').getOrCreate()
    assert spark.version >= '3.0' # make sure we have Spark 3.0+
    spark.sparkContext.setLogLevel('WARN')
    sc = spark.sparkContext
    main()