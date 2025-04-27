package org.example

import org.apache.spark.{SparkConf, SparkContext}

object WordCount {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setAppName("Word Count")
    val sc = new SparkContext(conf)

    val inputFile = "hdfs://user/prod_ghq/mr_data.txt"  // HDFS中的输入文件路径
    val textFile = sc.textFile(inputFile)

    val wordCounts = textFile.flatMap(line => line.split(" "))
      .map(word => (word, 1))
      .reduceByKey(_ + _)

    wordCounts.saveAsTextFile("hdfs://user/prod_ghq/mr_data_out.txt")  // HDFS中的输出文件路径

    sc.stop()
  }
}

