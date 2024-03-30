package org.lang.scala.rockjvm

/**
 * custom Interpolator，define implicit class
 *
 * @author Sam Ma
 * @date 2022/09/23
 */
object Interpolator {

  // s-interpolator
  val lifeOfPi = 3.14159
  val sInterpolator = s"The value of PI is $lifeOfPi, the half of PI is ${lifeOfPi / 2}"

  // raw interpolator
  val rawInterpolator = raw"The value of PI is $lifeOfPi\n <-- this is not a newline"
  // f-interpolator ~ printf
  val fInterpolator = f"The approximate value of Pi is $lifeOfPi%4.2f"

  // a custom interpolator, "name,age" -> Person
  case class Person(name: String, age: Int)
  // "normal" approach, 正常情况下进行显示转换，用fromStringToPerson函数
  /*def fromStringToPerson(line: String): Person = {
    val tokens = line.split(",")
    Person(tokens(0), tokens(1).toInt)
  }*/

  // structure for custom interpolator, Person(Bob,34)
  implicit class PersonInterpolator(sc: StringContext) {
    def person(args: Any*): Person = { // logic inside
      val parts = sc.parts // the things in between the args
      val totalString = sc.s(args: _*) // total expanded string

      val tokens = totalString.split(",")
      Person(tokens(0), tokens(1).toInt)
    }
  }

  val name = "Bob"
  val age = 34
  val bobInterpolator = person"$name,$age"

  def main(args: Array[String]): Unit = {
    println(sInterpolator)
    println(rawInterpolator)
    println(fInterpolator)
    println(bobInterpolator)
  }

}
