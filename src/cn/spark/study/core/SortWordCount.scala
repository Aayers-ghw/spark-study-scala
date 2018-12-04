package cn.spark.study.core

import org.apache.spark.SparkConf
import org.apache.spark.SparkContext

object SortWordCount {
  def main(args: Array[String]){
    val conf = new SparkConf()
        .setAppName("SortWordCount")
        .setMaster("local")
    val sc = new SparkContext(conf)
    
    val lines = sc.textFile("spark.txt", 1)
    val words = lines.flatMap(line ⇒ line.split(" "))
    val pairs = words.map(word ⇒ (word, 1))
    val wordCounts = pairs.reduceByKey(_ + _)
    
    val countWords = wordCounts.map(wordCount ⇒ (wordCount._2, wordCount._1))
    val sortedCountWords = countWords.sortByKey(false, 1)
    val sortedWordCounts = sortedCountWords
            .map(sortedWordCount ⇒ (sortedWordCount._2, sortedWordCount._1))
  
    //上面三步可以用这一步代替
//    val sortedWordCounts = wordCounts.sortBy(_._2, false)
    
    sortedWordCounts.foreach(sortedWordCount ⇒ {
      println(sortedWordCount._1 + " appear " + sortedWordCount._2 + "times") 
    })
    
  }
}