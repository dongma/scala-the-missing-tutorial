package org.lang.scala.rockjvm

/**
 * Why are Type Classes useful in Scala? | Rock the JVM
 *
 * @author Sam Ma
 * @date 2022/09/23
 */
object WhyAreTypeClassesUseful {

  // implicits, define summable trait, the implementation are IntSummable and StringSummable
  trait Summable[T] {
    def sumElements(list: List[T]): T
  }

  implicit object IntSummable extends Summable[Int] {
    override def sumElements(list: List[Int]): Int = list.sum
  }

  implicit object StringSummable extends Summable[String] {
    override def sumElements(list: List[String]): String = list.mkString("")
  }

  // problem specialized implementations
  def processMyList[T](list: List[T])(implicit summable: Summable[T]): T = {
    // "sum up" all the elements of the list, for integers => sum = actual sum of elements
    // for strings => sum = CONCATENATION of all the elements

    // for other types => ERROR
    summable.sumElements(list) // <-- here
  }

  def main(args: Array[String]): Unit = {
    val intSum = processMyList(List(1, 2, 3))
    val stringSum = processMyList(List("Scala ", "is ", "awesome"))
    println(intSum)
    println(stringSum)
    // processMyList(List(true, true, false)) ERROR at COMPILE TIME
  }

}
