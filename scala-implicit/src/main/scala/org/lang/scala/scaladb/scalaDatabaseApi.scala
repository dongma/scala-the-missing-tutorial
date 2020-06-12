package org.lang.scala.scaladb

/**
 * @author Sam Ma
 * @date 2020/06/11
 * 使用scala实现更加通用的泛型 get[T]可根据任意colName返回任意value数值
 */
object scalaDatabaseApi {

  import implicits._
  def main(args: Array[String]): Unit = {
    val row = javadatabase.JRow("one" -> 1, "two" -> 2.2, "three" -> "THREE!")

    val valueOne1: Int = row.get("one")
    val twoValue: Double = row.get("two")
    val threeValue: String = row.get("three")

    println(s"oneValue -> $valueOne1, twoValue -> $twoValue, threeValue -> $threeValue")

    // 使用了隐式类ScalaRow对Java的JRow类进行封装，在封装类中提供了get[T]方法，该类提供了隐式转换
    val oneValue2 = row.get[Int]("one")
    val twoValue2 = row.get[Double]("two")
    val threeValue2 = row.get[String]("three")

    println(s"oneValue2 -> $oneValue2, twoValue -> $twoValue2, threeValue -> $threeValue2")
  }

}

object implicits {
  import javadatabase.JavaRow

  // 在scala中实现通用的get[T],根据colName字段从结果集中取任意的colName
  implicit class ScalaRow(javaRow: JavaRow) {
    def get[T](colName: String)(implicit toT: (JavaRow, String) => T): T =
      toT(javaRow, colName)
  }

  implicit val jrowToInt: (JavaRow, String) => Int =
    (javaRow: JavaRow, colName: String) => javaRow.getInt(colName)
  implicit val jrowToDouble: (JavaRow, String) => Double =
    (javaRow: JavaRow, colName: String) => javaRow.getDouble(colName)
  implicit val jrowToString: (JavaRow, String) => String =
    (javaRow: JavaRow, colName: String) => javaRow.getText(colName)
}
