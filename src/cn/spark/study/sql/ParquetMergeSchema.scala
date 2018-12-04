package cn.spark.study.sql

import org.apache.spark.SparkConf
import org.apache.spark.SparkContext
import org.apache.spark.sql.SQLContext
import org.apache.spark.sql.SaveMode

object ParquetMergeSchema {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf()
        .setAppName("ParquetMergeSchema")
    val sc = new SparkContext(conf)
    val sqlContext = new SQLContext(sc)
    
    import sqlContext.implicits._
    
    // 创建一个DataFrame，作为学生的基本信息，并写入一个parquet文件中
    val studentWithNameAge = Array(("leo", 23), ("jack", 25)).toSeq
    val studentWithNameAgeDF = sc.parallelize(studentWithNameAge, 2).toDF("name", "age")
    studentWithNameAgeDF.save("hdfs://hadoop-3:9000/spark-study/students", "parquet", SaveMode.Append)
    
     // 创建第二个DataFrame，作为学生的成绩信息，并写入一个parquet文件中
    val studentWithNameGrade = Array(("marry", "A"), ("tom", "B")).toSeq
    val studentWithNameGradeDF = sc.parallelize(studentWithNameGrade, 2).toDF("name", "grade")
    studentWithNameGradeDF.save("hdfs://hadoop-3:9000/spark-study/students", "parquet", SaveMode.Append)
    
    // 首先，第一个DataFrame和第二个DataFrame的元数据肯定是不一样的吧
    // 一个是包含了name和age两个列，一个是包含了name和grade两个列
    // 所以， 这里期望的是，读取出来的表数据，自动合并两个文件的元数据，出现三个列，name、age、grade
    
    // 用mergeSchema的方式，读取students表中的数据，进行元数据的合并
    val students = sqlContext.read.option("mergeSchema", "true")
                    .parquet("hdfs://hadoop-3:9000/spark-study/students")
    students.printSchema()
    students.show()
  }
}