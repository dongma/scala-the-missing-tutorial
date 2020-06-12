package org.lang.scala
import math.Ordering
/**
 * @author Sam Ma
 * @date 2020/06/10
 * scala中的隐式参数，在函数调用时进行省略默认引用作用域内implicit对象
 */
object ImplicitArgs {
  def main(args: Array[String]): Unit = {
    {
      // 引入隐式参数rate，在当前作用域中每次调用calcTax方法时若未传递参数，则默认使用作用域中的隐式参数
      import SimpleStateSalesTax.rate
      val amount = 100F
      println(s"Tax on $amount = ${calcTax(amount)}")
    }

    {
      // 向当前作用域中导入默认的隐式方法rate，用于复杂税率值的计算
      import ComplicatedSalesTax.rate
      implicit val myStore = ComplicatedSalesTaxData(0.06F, false, 1010)
      val amount = 100F
      println(s"Tax on $amount = ${calcTax(amount)}")
    }

    // 通过scala的隐式参数对List集合内容进行排序(这块没有理解)
    val list = MyList(List(1, 3, 5, 2, 4))
    list.sortBy1(i => -i)
    println("sortBy1 value: " + list)

    val listMap = List("one" -> 1, "two" -> 2, "three" -> 3)
    println(s"list to Map: ${listMap.toMap}")

    // 为避免jvm的类型擦除内容，使用TypeErasure隐式根据不同的list进行打印
    import TypeErasure._
    method(List(1, 2, 3))
    method(List("one", "two", "three"))
  }

  def calcTax(amount: Float)(implicit rate: Float): Float = amount * rate
}

object SimpleStateSalesTax {
  implicit val rate: Float = 0.05F
}

case class ComplicatedSalesTaxData(baseRate: Float, isTaxHoliday: Boolean, storedId: Int)

object ComplicatedSalesTax {
  private def extractTaxRateForStore(id: Int): Float = {
    0.0F
  }

  implicit def rate(implicit cstd: ComplicatedSalesTaxData): Float =
    if (cstd.isTaxHoliday) 0.0F
    else cstd.baseRate + extractTaxRateForStore(cstd.storedId)
}

// implicitly方法与附加类型签名相结合，便能以一种快捷的方式定义一个接收参数化类型隐式参数的函数
case class MyList[A](list: List[A]) {
  def sortBy1[B](f: A => B)(implicit ord: Ordering[B]): List[A] =
    list.sortBy(f)(ord)

  // 参数B:Ordering被称为上下文定界，它暗指第二个参数列表(也就是哪个隐式参数列表)将接受Ordering[B]实例
  def sortBy2[B: Ordering](f: A => B): List[A] = list.sortBy(f)(implicitly[Ordering[B]])
}

object TypeErasure {
  implicit object IntMarker
  implicit object StringMarker

  def method(seq: Seq[Int])(implicit i: IntMarker.type): Unit = println(s"Seq[Int]: $seq")
  def method(seq: Seq[String])(implicit s: StringMarker.type): Unit = println(s"Seq[String]: $seq")
}