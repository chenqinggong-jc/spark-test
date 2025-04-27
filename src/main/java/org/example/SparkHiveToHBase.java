package org.example;

import org.apache.spark.sql.*;
import org.apache.hadoop.conf.Configuration;

public class SparkHiveToHBase {
    public static void main(String[] args) {
        // 配置HBase连接信息
        Configuration hbaseConfig = new Configuration();
        hbaseConfig.set("hbase.zookeeper.quorum", "192.168.173.95"); // HBase Zookeeper的地址
        hbaseConfig.set("hbase.zookeeper.property.clientPort", "2181"); // HBase Zookeeper的端口号

// 设置HBase配置到SparkSession

        SparkSession spark = SparkSession.builder()
                .appName("SparkHiveToHBase")
                .enableHiveSupport()
                .getOrCreate();
                spark.sparkContext()
                .hadoopConfiguration()
                .addResource(hbaseConfig);

        Dataset<Row> hiveData = spark.sql("SELECT id,company_name  FROM swhy.orig_table");

        // 将Hive数据写入HBase
        hiveData.write()
                .format("org.apache.hadoop.hbase.spark")
                .option("hbase.table", "swhy_table")
                .option("hbase.columns.mapping", "COLUMN_FAMILY:id,COLUMN_FAMILY:mc")  // 映射HBase的列族和列
                .save();

        spark.stop();
    }
}