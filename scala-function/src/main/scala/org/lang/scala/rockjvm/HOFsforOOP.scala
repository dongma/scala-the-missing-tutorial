package org.lang.scala.rockjvm

/**
 * high order function for scala, 高阶函数 (函数间嵌套调用)
 *
 * @author Sam Ma
 * @date 2022/09/19
 */
object HOFsforOOP {

  class Applicable {
    def apply(x: Int) = x + 1
  }
  val applicable = new Applicable
  applicable.apply(1) // 2
  applicable(1) // 2, 实际上是调用Applicable#apply方法

  // scala function objects, compiler提示说可以用语法糖 syntactic sugar
  val incrementer = new Function1[Int, Int] {
    override def apply(x: Int) = x + 1
  }
  incrementer.apply(2) // 3
  incrementer(2) // 3

  // syntax sugar, 用lambda表达式实现Function1
  val incrementAlt = (x: Int) => x + 1 // new Function1[Int, Int]{apply = ...}
  incrementAlt(2) // 3
  incrementAlt.apply(2) // 3

  // Example/exercise/, g=nTimes(f, 30), g(x)=f(f(f(....(f(x))))) 30 times
  def nTimes(f: Int => Int, n: Int): Int => Int = {
    if (n <= 0) (x: Int) => x
    else (x: Int) => nTimes(f, n - 1)(f(x))
  }

  // Q2 - 定义fun4函数，func函数逐个嵌套
  val func = (x: Int) => x + 1
  val fun4 = nTimes(func, 4) // x => f(f(f(f(x))))
  val f4Alt = (x: Int) => nTimes(func, 3)(func(x)) // new Function1[Int, Int]{apply = nTimes(func,3)(func(x))}
  fun4(5)

  // Q3 - 原始nTimes函数调用
  def nTimesOriginal(f: Function1[Int, Int], n: Int): Function1[Int, Int] =
    if (n <= 0)
      new Function1[Int, Int] {
        override def apply(x: Int) = x
      } // JVM Object
    else
      new Function1[Int, Int] {
        override def apply(x: Int): Int = nTimesOriginal(f, n - 1).apply(f(x))
      }

  def main(args: Array[String]): Unit = {

  }

}

