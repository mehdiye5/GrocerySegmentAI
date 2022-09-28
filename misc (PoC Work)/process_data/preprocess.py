import boto3
import pandas as pd
from io import StringIO
import json

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
    df1, df2, df3 = return_df('data.json')
    print("returned")
    print(df1.head(5))
