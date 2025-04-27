package org.example;

import org.apache.spark.sql.SparkSession;

public class Main3 {
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
        spark.sql("SELECT * FROM devdb.cdg_cst_map_comcode  where com_code='"+param1+"';").show();
        spark.close();
        spark.stop();
    }
}