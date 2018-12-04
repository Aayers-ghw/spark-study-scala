package cn.spark.study.core

import org.apache.spark.SparkConf
import org.apache.spark.SparkContext
import org.apache.spark.SparkContext._

object LineCount {
  def main(args: Array[String]): Unit = {
    
    val conf = new SparkConf()
             .setAppName("LineCount")
             .setMaster("local")
    val sc = new SparkContext(conf)
    val lines = sc.textFile("hello.txt", 1)
    val pairs = lines.map(line ⇒ (line, 1))
    val linecounts = pairs.reduceByKey( _ + _ )
    
    linecounts.foreach(linecount ⇒ println(linecount._1 + " appears " + linecount + " times."))
  }
}