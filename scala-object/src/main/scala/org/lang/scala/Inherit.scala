package org.lang.scala

import org.slf4j.LoggerFactory

/**
 * @author Sam Ma
 * @date 2020/06/27
 * scala面向对象编程 AnyVal和AnyRef类型，使用extends关键字 call parent constructor method
 */
object Inherit {

  private val logger = LoggerFactory.getLogger(this.getClass)

  def main(args: Array[String]): Unit = {
    // default value简化参数
    val address = new Address("98765")
    val person = Person("Buck Trends", Some(20), Some(address))
    logger.info(s"person object with default value: {}", person)
    // 调用父类构造器
    val employee = new Employee("Buck Treads", Some(20),
      Some(address), "Zombie Dev")
    logger.info(s"use extends to call parent's constructor method: {}", employee)
  }

}

/**
 * 引用类型都为AnyRef的子类型
 */
trait Digitizer extends Any {
  def digits(s: String): String = s.replaceAll("""\D""", "")
}
/**
 * trait与java中的interface类似
 */
trait Formatter extends Any {
  def format(areaCode: String, exchange: String, subNumber: String): String =
    s"($areaCode) $exchange-$subNumber"
}

class USPhoneNumber(val s: String) extends AnyVal with Digitizer with Formatter {
  override def toString: String = {
    val digitsValue = digits(s)
    val areaCode = digitsValue.substring(0, 3)
    val exchange = digitsValue.substring(3, 6)
    val subNumber = digitsValue.substring(6, 10)
    format(areaCode, exchange, subNumber)
  }
}

/**
 * object 是case class的伴随对象
 */
case class Address(street: String, city: String, state: String, zip: String) {
  def this(zip: String) =
    this("[unknown]", Address.zipToCity(zip), Address.zipToState(zip), zip)
}

/**
 * 用object实现static method
 */
object Address {
  def zipToCity(zip: String) = "Anytown"

  def zipToState(zip: String) = "CA"
}


/**
 * Person类的定义
 */
case class Person(name: String, age: Option[Int] = None,
                  address: Option[Address] = None) {
}
/**
 * case类的继承
 */
class Employee(name: String,
               age: Option[Int] = None,
               address: Option[Address] = None,
               val title: String = "[unknown]",
               val manager: Option[Employee] = None) extends Person(name, age, address) {
  // 需要override toString()方法，否则其就会调用Person.toString()方法
  override def toString: String =
    s"Employee($name, $age, $address, $title, $manager)"

}