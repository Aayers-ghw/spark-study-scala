package cn.spark.study.sql

import org.apache.spark.SparkConf
import org.apache.spark.SparkContext
import org.apache.spark.sql.SQLContext

object DataFrameOperation {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf()
              .setAppName("DataFrameCreate")
    val sc = new SparkContext(conf)
    val sqlContext = new SQLContext(sc)
    
    val df = sqlContext.read.json("hdfs://hadoop-3:9000/students.json")
    
    df.show()
    df.printSchema()
    df.select("name").show()
    df.select(df("name"), df("age") + 1).show()
    df.filter(df("age") > 18).show()
    df.groupBy(df("age")).count().show()
  }
}