package cn.spark.study.core

import org.apache.spark.SparkConf
import org.apache.spark.SparkContext

object ParallelizeCollection {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf()
                .setAppName("ParallelizeCollection")
                .setMaster("local")
    val sc = new SparkContext(conf);
    val numbers = Array(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
    val numbersRDD = sc.parallelize(numbers, 5)
    val sum = numbersRDD.reduce{_ + _}
    println(sum)
  }
  
}