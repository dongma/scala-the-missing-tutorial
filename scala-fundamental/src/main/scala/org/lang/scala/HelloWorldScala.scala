package org.lang.scala

import scala.annotation.tailrec

/**
 * @author Sam Ma
 * @date 2020/06/02
 * scala应用程序的主类，用于scala基础语法的学习
 */
object HelloWorldScala {

  def main(args: Array[String]): Unit = {
    println("Hello, world, scala!")

    // scala中变量的声明, 对于不可变的变量使用val进行声明，但array item引用对象可以改变
    val array: Array[String] = new Array[String](5);
    array(0) = "hello"
    println("val array item 0: " + array(0))

    // 对于可变引用对象需要使用var进行声明，在首次初始化后可再次初始化变量
    var stockPrice: Double = 100.0
    stockPrice = 200.0
    println("var stockPrice item: " + stockPrice)

    // 声明一个class对象，其中对与类的实例化是与java类似的，获取类成员属性可直接通过.操作符获取
    class Person(val name: String, val age: Int)
    val person = new Person("Dean Wampler", 20)
    println("person.name: " + person.name + ", person.age: " + person.age)
    // person.name = "Buck Trends"，val是不能改变引用字段信息

    // scala中的偏函数：偏函数在与其并不处理所有可能的输入，而只处理那些能至少与一个case语句匹配的输入
    val pf1: PartialFunction[Any, String] = {
      case s: String => "yes"
    }
    val pf2: PartialFunction[Any, String] = {
      case d: Double => "yes"
    }
    val pf = pf1 orElse pf2

    def tryPF(x: Any, f: PartialFunction[Any, String]): String =
      try {
        f(x).toString
      } catch {
        case _: MatchError => "Error"
      }

    def d(x: Any, f: PartialFunction[Any, String]) =
      f.isDefinedAt(x).toString
    // 通过scala List使用实际数据执行偏函数，pf1只匹配String类型 pf2匹配Double类型，通过pf1 orElse pf2将其拼接可同时匹配两种类型
    List("str", 3.14, 10) foreach { x =>
      println("[" + x.toString + "]: " + d(x, pf1) + ", " + tryPF(x, pf1) + ", " + d(x, pf2) + ", " + tryPF(x, pf2) + ","
        + d(x, pf) + ", " + tryPF(x, pf))
    }

    // scala中的方法声明，显示使用命名参数列表进行初始化Point类实例，可以指定全部参数 也可以指定部分参数
    val point1 = new Point(x = 3.3, y = 4.4)
    val point2 = point1.copy(y = 6.6);
    println("point1: [" + point1 + "], point2: [" + point2 + "]")

    // scala方法具有多个参数列表，使用圆弧原点、圆的半径进行初始化
    val shape = new Circle(point1, 1.2)
    shape.draw(Point(1.0, 2.0))(str => println(s"ShapesDrawingActor: $str"))
    // 此外scala还允许将参数列表两边的圆括号替换为花括号，{...}花括号表示的内容为我们传递给draw方法的参数
    shape.draw(Point(1.0, 2.0)) { str => println(s"ShapesDrawingActor: $str") }
    // warning: 可以通过参数命名来进行调用，但参数f无法识别 不能通过编译
//    shape.draw(f = str => println(s"ShapeDrawingActor: $str"))

    // 使用foreach语法对0～5的整数值进行累乘，并将结果进行输出
    (0 to 5) foreach (int => print(factorial(int) + " "))

    println(StringUtilsV1.joiner(List("Programing", "Scala")))

    // sealed关键字的使用，其告诉编译器：所有的子类必须在同一个源文件中声明
    sealed abstract class Option[+A] {}

    // 在scala中导入以来class使用import关键字，使用下划线_作为通配符 与java中的*功能相同
    import java.util.{ArrayList, HashMap}
    import java.awt._

  }

  /**
   * scala中方法的声明，定义Point类并提供默认的初始值
   *
   * @param x
   * @param y
   */
  case class Point(x: Double = 0.0, y: Double = 0.0) {
    def shift(deltax: Double = 0.0, deltay: Double = 0.0) =
      copy(x + deltax, y + deltay)
  }

  sealed abstract class Shape() {
    /**
     * draw带有两个参数列表，其中一个参数列表带着一个表示绘制偏移量的参数，另一个参数列表是我们之前
     * 用过的函数参数
     *
     * @param offset
     * @param f
     */
    def draw(offset: Point = Point(0.0, 0.0))(f: String => Unit): Unit =
      f(s"draw(offset = $offset), ${this.toString}")
  }

  case class Circle(center: Point, radius: Double) extends Shape

  case class Rectangle(lowerLeft: Point, height: Double, width: Double) extends Shape

  /**
   * 在scala中实现方法递归调用，使用@tailrec注解对递归进行检测
   *
   * @param intValue
   * @return
   */
  def factorial(intValue: Int): Long = {
    @tailrec
    def fact(value: Int, accumulator: Int): Long = {
      if (value <= 1) accumulator
      else fact(value - 1, value * accumulator)
    }

    fact(intValue, 1)
  }

  /**
   * scala中的类型推断，当调用其它函数有明确的返回类型时，需要在返回值类型中明确显示
   */
  object StringUtilsV1 {
    def joiner(strings: String*): String = strings.mkString("-")

    // 出现编译错误：Error:(123, 47) overloaded method joiner needs result type
    //    def joiner(strings: List[String]) = joiner(strings: _*)，由于调用第一个joiner方法，需显示地将返回值声明为String
    def joiner(strings: List[String]): String = joiner(strings: _*)
  }

}
