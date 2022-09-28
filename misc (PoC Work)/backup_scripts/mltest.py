import sys
assert sys.version_info >= (3, 5) # make sure we have Python 3.5+

from pyspark.sql import SparkSession, functions, types #type:ignore

def main():

#p_reordered_ratio,reordered,aisle_id,department_id
    data_schema = types.StructType([
        types.StructField('index', types.IntegerType(), True),
        types.StructField('user_id', types.IntegerType(), True),
        types.StructField('product_id', types.IntegerType(), True),
        types.StructField('uxp_times_bought', types.IntegerType(), True),
        types.StructField('uxp_reordered_ratio', types.FloatType(), True),
        types.StructField('u_num_of_orders', types.IntegerType(), True),
        types.StructField('u_avg_prd', types.FloatType(), True),
        types.StructField('dow_most_orders_u', types.IntegerType(), True),
        types.StructField('hod_most_orders_u', types.IntegerType(), True),
        types.StructField('prd_count_p', types.IntegerType(), True),
        types.StructField('p_reordered_ratio', types.FloatType(), True),
        types.StructField('reordered', types.FloatType(), True),
        types.StructField('aisle_id', types.IntegerType(), True),
        types.StructField('department_id', types.IntegerType(), True)
    ])

    train = spark.read.csv('/Users/brendanartley/dev/733/final_project/archive/train.csv', schema=data_schema, header=True)
    valid = spark.read.csv('/Users/brendanartley/dev/733/final_project/archive/valid.csv', schema=data_schema, header=True)

    pass

if __name__ == '__main__':
    spark = SparkSession.builder.appName('example code').getOrCreate()
    assert spark.version >= '3.0' # make sure we have Spark 3.0+
    spark.sparkContext.setLogLevel('WARN')
    sc = spark.sparkContext
    main()