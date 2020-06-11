package org.lang.scala.scaladb

/**
 * @author Sam Ma
 * @date 2020/06/11
 *
 */
package javadatabase {

  import org.lang.scala.database_api._

  case class JavaRow(representation: Map[String, Any]) extends Row {
    private def get(colName: String): Any =
      representation.getOrElse(colName, throw InvalidColumnName(colName))

    def getInt(columnName: String): Int = get(columnName).asInstanceOf[Int]

    def getDouble(colName: String): Double = get(colName).asInstanceOf[Double]

    def getText(colName: String): String = get(colName).asInstanceOf[String]

  }

  object JRow {
    def apply(pairs: (String, Any)*) = new JavaRow(Map(pairs: _*))
  }

}
