package org.lang.scala.scaladb

/**
 * @author Sam Ma
 * @date 2020/06/11
 * 定义实现JRow使用Map作为结果集，同时在Map中根据colName字段获取相应的value
 */
package javadatabase {

  import org.lang.scala.database_api._

  // 实现get(colName)方法内容，其会根据字段名称去Map中查找该colName对应的value值
  case class JavaRow(representation: Map[String, Any]) extends Row {
    private def get(colName: String): Any =
      representation.getOrElse(colName, throw InvalidColumnName(colName))

    def getInt(columnName: String): Int = get(columnName).asInstanceOf[Int]

    def getDouble(colName: String): Double = get(colName).asInstanceOf[Double]

    def getText(colName: String): String = get(colName).asInstanceOf[String]

  }

  // scala实现这个api时，使用Map表示结果集中的一行(模拟数据表中一行记录)
  object JRow {
    def apply(pairs: (String, Any)*) = new JavaRow(Map(pairs: _*))
  }

}
