package org.lang.scala.scaladb

import org.lang.scala.scaladb.javadatabase._

/**
 * @author Sam Ma
 * @date 2020/06/11
 *
 */
object scalaDatabaseApi {

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
