package cn.spark.study.core

class SecondarySortKey(val first : Int, val second : Int) 
       extends Ordered[SecondarySortKey] with Serializable{
  def compare(that : SecondarySortKey) : Int = {
    if(this.first - that.first != 0){
      return this.first - that.first
    }else{
      return this.second - that.second
    }
  }
}