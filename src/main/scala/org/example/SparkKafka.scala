package org.example

import org.apache.log4j.{LogManager, Logger}
import org.apache.spark.SparkConf
import org.apache.spark.streaming.{Seconds, StreamingContext}
import org.apache.spark.streaming.kafka010.{ConsumerStrategies, KafkaUtils, LocationStrategies}

object SparkKafka {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setAppName("KafkaSparkStreaming")
    val ssc = new StreamingContext(conf, Seconds(5))


    @transient lazy val log = Logger.getLogger(getClass.getName)


    val kafkaParams = Map[String, Object](
      "bootstrap.servers" -> "192.168.173.102:9092",
      "key.deserializer" -> "org.apache.kafka.common.serialization.StringDeserializer",
      "value.deserializer" -> "org.apache.kafka.common.serialization.StringDeserializer",
      "group.id" -> "test-consumer-group",
      "auto.offset.reset" -> "latest",
      "enable.auto.commit" -> (false: java.lang.Boolean)
    )

    val topics = Array("input_topic1")
    val stream = KafkaUtils.createDirectStream[String, String](
      ssc,
      LocationStrategies.PreferConsistent,
      ConsumerStrategies.Subscribe[String, String](topics, kafkaParams)
    )
    stream.map(record => record.value).print()

//    stream.map(record =>
//    val logger = LogManager.getLogger("MyLogger")
//    logger.info(record.value)
//    )

    stream.map { record =>
      val logger = LogManager.getLogger("MyLogger")
      logger.info(record.value)
    }

    ssc.start()
    ssc.awaitTermination()

  }

}
