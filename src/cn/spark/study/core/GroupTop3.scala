package cn.spark.study.core

import org.apache.spark.SparkConf
import org.apache.spark.SparkContext
import scala.util.control.Breaks.{break, breakable}

object GroupTop3 {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf()
          .setAppName("GroupTop3")
          .setMaster("local")
    val sc = new SparkContext(conf)
    
    val lines = sc.textFile("score.txt", 1)
    val pairs = lines.map(line ⇒ (line.split(" ")(0), line.split(" ")(1).toInt))
    val groupedPairs = pairs.groupByKey()
    
    val top3Score  = groupedPairs.map(line ⇒ {
      val className = line._1
      val top3 = Array[Int](3)
      
      val scores = line._2
      for(elem ← scores){
        
        for(i ← 0 until 3){
          if(top3(i) == null){
            top3(i) = elem
            break
          }else if(top3(i) < elem){
            
            for(j ← 2 until i){
              top3(j) = top3(j - 1)
            }
            top3(i) = elem
            break
            
          }
        }
      }
      new Tuple2(className, top3)
    })
    
    top3Score.foreach(lines ⇒ {
      println("class : " + lines._1)
      for(elem ← lines._2){
        println(elem)
      }
      println("==========================")
    })
    
  }
}