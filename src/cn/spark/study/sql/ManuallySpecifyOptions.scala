package cn.spark.study.sql

import scala.tools.scalap.Main
import org.apache.spark.SparkConf
import org.apache.spark.SparkContext
import org.apache.spark.sql.SQLContext

class ManuallySpecifyOptions {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf()
        .setAppName("ManuallySpecifyOptions")
    val sc = new SparkContext(conf)
    val sqlContext = new SQLContext(sc)
    
    val personDF = sqlContext.read.format("json")
        .load("hdfs://hadoop-3:9000/person.json")
    personDF.select("name").write.format("parquet")
        .save("hdfs://hadoop-3:9000/personName_scala.parquet")
    
  }
}