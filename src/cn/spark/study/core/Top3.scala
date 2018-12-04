package cn.spark.study.core

import org.apache.spark.SparkConf
import org.apache.spark.SparkContext

object Top3 {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf()
            .setAppName("Top3")
            .setMaster("local")
    val  sc = new SparkContext(conf)
    
    val lines = sc.textFile("top.txt", 1)
    
    val pairs = lines.map(line ⇒ new Tuple2(line.toInt, line))
    
    val sortedPairs = pairs.sortByKey(false)
    
    val sortedNumbers = sortedPairs.map(sortedPair ⇒  sortedPair._1)
    
    val sortedNumberList = sortedNumbers.take(3)
    
    sortedNumberList.foreach(num ⇒ println(num))
  }
}