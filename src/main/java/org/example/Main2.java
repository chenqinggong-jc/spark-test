package org.example;

import org.apache.spark.sql.SparkSession;

public class Main2 {
    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Usage: spark-submit [spark options] <jar> <param1>");
            System.exit(1);
        }

        String param1 = args[0];
        System.out.println("Parameter 1: " + param1);
        SparkSession spark = SparkSession.builder()
                .appName("Spark Print Parameters Application").enableHiveSupport()
                .getOrCreate();
        spark.sql("select * from  ods.m_plugin_mwg_24  where id='"+param1+"'").show();
        spark.close();
        spark.stop();
    }
}