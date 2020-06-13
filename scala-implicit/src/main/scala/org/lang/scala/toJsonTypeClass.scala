package org.lang.scala

/**
 * @author Sam Ma
 * @date 2020/06/13
 * 在scala中通过implicit为Person和Address类提供toJson方法，在不修改原有代码情况下添加额外功能
 */
object toJsonTypeClass {

  def main(args: Array[String]): Unit = {
    val address = Address("1 scala lane", "anytown")
    val person = Person("Buck Treeds", address)

    println(s"address.toJson value: ${address.toJson()}")
    println(s"person.toJson value: ${person.toJson()}")

    // 使用隐式转换将任意类型的list item转换称为String类型
    val list: List[Any] = List(1, 2.2F, "three", 'symbol)
    list foreach { (x: Any) =>
      try {
        println(s"$x: ${x.stringize}")
      } catch {
        case ex: java.lang.UnsupportedOperationException => println(ex)
      }
    }
  }

  // scala不允许同时使用implicit和case共同修饰class,由于case类不会执行通过隐式所自动生成的额外代码
  implicit class AddressToJson(address: Address) extends ToJson {
    override def toJson(level: Int): String = {
      val (outdent, indent) = indentation(level)
      s"""{
         |${indent}"street": "${address.street}"
         |${indent}"street": "${address.city}
         |$outdent}""".stripMargin
    }
  }

  implicit class PersonToJson(person: Person) extends ToJson {
    override def toJson(level: Int): String = {
      val (outdent, indent) = indentation(level)
      s"""{
         |${indent}"name": "${person.name}",
         |${indent}"address": ${person.address.toJson(level + 1)}
         |$outdent}""".stripMargin
    }
  }

  implicit class AnyStringizer(any: Any) extends Stringizer[Any] {
    override def stringize: String = any match {
      case s: String => s
      case i: Int => (i * 10).toString
      case f: Float => (f * 10.1).toString
      case other =>
        throw new UnsupportedOperationException(s"Can't stringize $other")
    }
  }

}

case class Address(street: String, city: String)

case class Person(name: String, address: Address)

trait ToJson {
  def toJson(level: Int = 0): String

  val INDENTATION = " "
  def indentation(level: Int = 0): (String, String) = (INDENTATION * level, INDENTATION * (level + 1))
}

trait Stringizer[+T] {
  def stringize: String
}
