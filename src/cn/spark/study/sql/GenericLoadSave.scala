package cn.spark.study.sql

import org.apache.spark.SparkConf
import org.apache.spark.SparkContext
import org.apache.spark.sql.SQLContext

object GenericLoadSave {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf()
        .setAppName("GenericLoadSave")
        .setMaster("local")
    val sc = new SparkContext(conf)
    val sqlContext = new SQLContext(sc)
    
    val usersDF = sqlContext.read
            .load("users.parquet")
    usersDF.select("name", "favorite_color")
            .write.save("namesAndFavColors.parquet")
  }
}