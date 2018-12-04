package cn.spark.study.streaming

import org.apache.spark.SparkConf
import org.apache.spark.streaming.StreamingContext
import org.apache.spark.streaming.Durations

object WindowHotWord {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf()
        .setMaster("local[2]")
        .setAppName("WindowHotWord")
    val ssc = new StreamingContext(conf ,Durations.seconds(5))
    
    val searchLogsDStream = ssc.socketTextStream("localhost", 9999)
    val searchWordsDStream = searchLogsDStream.map(_.split(" ")(1))
    val searchWordPairsDStream = searchWordsDStream.map(searchWord ⇒ (searchWord, 1))
    val searchWordCountsDSteram = searchWordPairsDStream.reduceByKeyAndWindow(
        (v1 : Int, v2 : Int) ⇒ v1 + v2,
        Durations.seconds(60),
        Durations.seconds(10))
    val finalDStream = searchWordCountsDSteram.transform(searchWordCountsRDD ⇒ {
      val countSearchWordsRDD = searchWordCountsRDD.map(tuple ⇒ (tuple._2, tuple._1))
      val sortedCountSearchWordsRDD = countSearchWordsRDD.sortByKey(false)
      val sortedSearchWordCountsRDD = sortedCountSearchWordsRDD.map(tuple ⇒ (tuple._2, tuple._1))
      
      val top3SearchWordCounts = sortedSearchWordCountsRDD.take(3)
      
      println("top3")
       println("=====================")
      for(tuple ← top3SearchWordCounts){
        println(tuple)
      }
      println("=====================")
      searchWordCountsRDD
    })
    
    finalDStream.print()
    
    ssc.start()
    ssc.awaitTermination()
  }
}