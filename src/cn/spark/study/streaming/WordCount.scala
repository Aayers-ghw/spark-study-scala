package cn.spark.study.streaming

import org.apache.spark.SparkContext
import org.apache.spark.SparkConf
import org.apache.spark.streaming.StreamingContext
import org.apache.spark.streaming.Durations

object WordCount {
  
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf()
        .setMaster("local[2]")
        .setAppName("WordCount")
    val ssc = new StreamingContext(conf, Durations.seconds(5))    
    
    val lines = ssc.socketTextStream("localhost", 9999)
    val words = lines.flatMap(_.split(" "))
    val pairs = words.map(word ⇒ (word, 1))
    val wordCounts = pairs.reduceByKey(_ + _)
    
    wordCounts.print()
    
    ssc.start()
    ssc.awaitTermination()
  }
}