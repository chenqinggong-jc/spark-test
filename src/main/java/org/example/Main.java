package org.example;

import java.io.PrintStream;

import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.SparkSession.Builder;

public class Main {
    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Usage: spark-submit [spark options] <jar> <param1> <param2>");
            System.exit(1);
        }

        String param1 = args[0];
        System.out.println("Parameter 1: " + param1);
        SparkSession spark = SparkSession.builder()
                .appName("Spark Print Parameters Application").enableHiveSupport()
                .getOrCreate();
        spark.sql("alter table dwd_sdjt.dwd_sales_orders_imp drop partition(dt='"+param1+"');").show();
        spark.close();
        spark.stop();
    }
}