package cn.spark.study.core

import org.apache.spark.SparkConf
import org.apache.spark.SparkContext

object TransformationOperation {

  def map{
    
    val conf = new SparkConf()
            .setAppName("map")
            .setMaster("local")
    val sc = new SparkContext(conf)
    
    val numbers = Array(1, 2, 3, 4, 5, 6)
    val numberRDD = sc.parallelize(numbers, 1)
    val multipleNumberRDD = numberRDD.map(num ⇒ num * 2)
    
    multipleNumberRDD.foreach(num ⇒ println(num))
  }
  
  def filter{
    
    val conf = new SparkConf()
            .setAppName("filter")
            .setMaster("local")
    val sc = new SparkContext(conf)
    
    val numbers = Array(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
    val numberRDD = sc.parallelize(numbers, 1)
    val evenNumberRDD = numberRDD.filter(num ⇒  num % 2 == 0)
    
    evenNumberRDD.foreach( num ⇒ println (num))
  }
  
  def flatMap{
    val conf = new SparkConf()
            .setAppName("flatMap")
            .setMaster("local")
    val sc = new SparkContext(conf)
    
    val lineList = Array("hello you", "hello me", "hello world") 
    val lines = sc.parallelize(lineList, 1)
    val words = lines.flatMap(line ⇒ line.split(" "))
    
    words.foreach(word ⇒ println(word))
  }
  
  def groupByKey{
    
    val conf = new SparkConf()
            .setAppName("groupByKey")
            .setMaster("local")
    val sc = new SparkContext(conf)
    
    val scoreList = Array(Tuple2("class1", 80), Tuple2("class1", 80),
        Tuple2("class2", 75), Tuple2("class1", 90), Tuple2("class2", 65))
        
    val scores = sc.parallelize(scoreList, 1)
    val groupScores = scores.groupByKey()
    
    groupScores.foreach(score ⇒ {
      println(score._1)
      score._2.foreach(singleScore ⇒ println(singleScore))
      println("===========================")
    })
    
  }
  
  def reduceByKey{
    val conf = new SparkConf()
            .setAppName("reduceByKey")
            .setMaster("local")
    val sc = new SparkContext(conf)
    
    val scoreList = Array(Tuple2("class1", 80), Tuple2("class1", 80),
        Tuple2("class2", 75), Tuple2("class1", 90), Tuple2("class2", 65))
    
    val scores = sc.parallelize(scoreList, 1)
    val totalScore = scores.reduceByKey(_ + _)
    totalScore.foreach(classScore ⇒ println(classScore._1 + " " + classScore._2))
  }
  
  def sortByKey{
     val conf = new SparkConf()
            .setAppName("sortByKey")
            .setMaster("local")
    val sc = new SparkContext(conf)
    
    val scoreList = Array(
        Tuple2(65, "leo"), Tuple2(50, "tom"),
        Tuple2(100, "marry"), Tuple2(80, "jack")
        )
    
    val scores = sc.parallelize(scoreList, 1)
    
    val sortedScores = scores.sortByKey(false)
    
    sortedScores.foreach(score ⇒ println(score._2 + " " + score._1))
  }
  
  def join{
    val conf = new SparkConf()
            .setAppName("join")
            .setMaster("local")
    val sc = new SparkContext(conf)
    
    val studentList = Array(
        Tuple2(1, "leo"),
				Tuple2(2, "tom"),
				Tuple2(3, "marry")
				)
    
		val scoresList = Array(
		    Tuple2(1, 100),
				Tuple2(2, 90),
				Tuple2(3, 60)
				)

		val students = sc.parallelize(studentList, 1)
		val scores = sc.parallelize(scoresList, 1)
		
		val studentScores = students.join(scores)
		
		studentScores.foreach(studentScore ⇒{
		   println("student id : " + studentScore._1)
		   println("student name : " + studentScore._2._1)
		   println("student score : " + studentScore._2._2)
		   println("===============================")
		})
  }
  
  def cogroup{
    val conf = new SparkConf()
            .setAppName("cogroup")
            .setMaster("local")
    val sc = new SparkContext(conf)
    
    val studentList = Array(
        Tuple2(1, "leo"),
				Tuple2(2, "tom"),
				Tuple2(3, "marry"),
				Tuple2(1, "jack"),
				Tuple2(2, "amy"),
				Tuple2(3, "tony")
				)
    
		val scoresList = Array(
		    Tuple2(1, 100),
				Tuple2(2, 90),
				Tuple2(3, 60),
				Tuple2(1, 70),
				Tuple2(2, 80),
				Tuple2(3, 50)
				)

		val students = sc.parallelize(studentList, 1)
		val scores = sc.parallelize(scoresList, 1)
		
		val studentScores = students.cogroup(scores)
		
		studentScores.foreach(studentScore ⇒ {
		  println("student id : " + studentScore._1)
		  println("student names : " + studentScore._2._1)
		  println("student scores : " + studentScore._2._2)
		  println("==================================")
		})
  }
  def main(args: Array[String]): Unit = {
//    map
//    filter
//    flatMap
//    groupByKey
//    reduceByKey
//    sortByKey
//    join
//    cogroup
  }
  
}