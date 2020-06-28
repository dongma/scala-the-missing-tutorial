package org.lang.scala

import org.slf4j.LoggerFactory

/**
 * @author Sam Ma
 * @date 2020/06/27
 * scala面向对象编程 AnyVal和AnyRef类型，使用extends关键字 call parent constructor method
 */
object OopInScala {

  private val logger = LoggerFactory.getLogger(this.getClass)

  def main(args: Array[String]): Unit = {
    val benjamin = new Dollar(100)
    logger.info(s"use AnyVal object to use native value: {}", benjamin)

    val number = new USPhoneNumber("987-654-3210")
    logger.info(s"use trait and AnyVal to format value: {}", number)

    // 在构造参数列表中使用 default value简化构造器参数列表, 在使用case类声明 实例化不需new关键字
    val address = new Address("98765")
    val person = Person("Buck Trends", Some(20), Some(address))
    logger.info(s"person object with default value: {}", person)

    // 调用父类构造器：在构造函数中通过extends关键字就可直接调用父类构造起方法
    val employee = new Employee("Buck Treads", Some(20), Some(address), "Zombie Dev")
    logger.info(s"use extends to call parent's constructor method: {}", employee)
  }

}

/*
 * scala在2.10中推出了价值类来解决"对值类型的包装，会将值类型变为引用类型从而失去原生类型的良好性能"问题
 *  anyVal class可以是一个case类，但额外生成的方法和伴随对象不大可能被用到 导致产生的class文件就被白白浪费了一些空间
 */
class Dollar(val value: Float) extends AnyVal {
  override def toString: String = "$%.2f".format(value)
}

/*
 * 在scala中所有引用类型都为AnyRef的子类型，所有值类型都为AnyValue的子类型 根类型为Any
 */
trait Digitizer extends Any {
  def digits(s: String): String = s.replaceAll("""\D""", "")
}

/*
 * trait关键字与java中的interface类似，用于声明接口 对于抽象类仍使用abstract keyword
 */
trait Formatter extends Any {
  def format(areaCode: String, exchange: String, subNumber: String): String =
    s"($areaCode) $exchange-$subNumber"
}

class USPhoneNumber(val s: String) extends AnyVal with Digitizer with Formatter {

  override def toString: String = {
    val digitsValue = digits(s)
    // 从USPhoneNumber给定的字符串中截取areaCode、exchange和subNumber串，然后将其进行拼接并返回
    val areaCode = digitsValue.substring(0, 3)
    val exchange = digitsValue.substring(3, 6)
    val subNumber = digitsValue.substring(6, 10)
    format(areaCode, exchange, subNumber)
  }
}

/*
 * object Address是case class的伴随对象，在case class中可调用object中定义的方法
 */
case class Address(street: String, city: String, state: String, zip: String) {
  def this(zip: String) =
    this("[unknown]", Address.zipToCity(zip), Address.zipToState(zip), zip)
}

/*
 * 在scala中没有static method和静态类，其可以使用object来实现该功能
 */
object Address {
  def zipToCity(zip: String) = "Anytown"

  def zipToState(zip: String) = "CA"
}

/*
 * 在case构造器使用Option[Int]可将参数作为可选，实例化时可省略传递此参数
 *  重新考虑Person类型可对列表中Option参数赋予默认值None, 这样可简化构造函数内容
 */
case class Person(name: String, age: Option[Int] = None, address: Option[Address] = None) {
  /*def this(name: String) = this(name, None, None)

  def this(name: String, age: Int) = this(name, Some(age), None)

  def this(name: String, age: Int, address: Address) =
    this(name, Some(age), Some(address))

  def this(name: String, address: Address) = this(name, None, Some(address))*/
}

/*
 * 调用父类构造器 (与良好的面向对象设计)，在主构造函数参数前加上val或var关键字，该参数就成为实例的一个字段
 */
class Employee(
                name: String,
                age: Option[Int] = None,
                address: Option[Address] = None,
                val title: String = "[unknown]",
                val manager: Option[Employee] = None) extends Person(name, age, address) {

  // 需要override toString()方法，否则其就会调用Person.toString()方法
  override def toString: String =
    s"Employee($name, $age, $address, $title, $manager)"

}