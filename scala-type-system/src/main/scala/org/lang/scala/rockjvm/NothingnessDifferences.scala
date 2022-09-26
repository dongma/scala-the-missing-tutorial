package org.lang.scala.rockjvm

/**
 * Null、Nothing、Nil、None和Unit的区别
 *
 * @author Sam Ma
 * @date 2022/09/26
 */
object NothingnessDifferences {

  // 1- the null reference, java.lang.ExceptionInInitializerError
  val anAbsentString: String = null
//  anAbsentString.length

  // 2- AnyRef -> all reference types -> Null = the type of the null reference
  class Person
  val theNullReference: Null = null
  val noString: String = theNullReference
  val noPerson: Person = theNullReference
  val noList: List[Int] = theNullReference  // Null has special treatment by the compiler

  // 3- Nil可用于实例化任意empty的List，Nil也具有method和field属性, Nil = the empty list
  val anEmptyList: List[Int] = Nil
  println(Nil.length)

  // 4- None is the subtype of Options, None和Some为具体值，是Option的子类型
  val anAbsentInt: Option[Int] = None
  val aPresentInt: Option[Int] = Some(42)
  println(anAbsentInt.isEmpty)

  // 5- Unit理解，有些类似于Java的void关键字
  def aUnitReturningMethod(): Unit = println("I'm starting to get the difference")

  // 6- Nothing = No value at all, throw expressions return Nothing
  // Nothing != Unit != Null != anything at all, Nothing is the type of "nothingness"
  /*val nothingInt: Int = throw new RuntimeException("No int")  // return Nothing
  val nothingString: String = throw new RuntimeException("No String") // return Nothing*/

  class MyPrecious  // extends AnyRef
  def gimmePrecious(): MyPrecious = null
  def aFunctionAboutNothing(item: Nothing): Int = 45

  // Nothing is useful in GENERICS, in COVARIANT GENERICS
  abstract class MyList[+T] // if Dog extends Animal, the List[Dog] <: List[Animal]
  class NonEmptyList[+T](head: T, tail: MyList[T])
  object EmptyList extends MyList[Nothing]

  val listOfStrings: MyList[String] = EmptyList
  val listOfIntegers: MyList[Int] = EmptyList
  val preciousList: MyList[MyPrecious] = EmptyList

  // `???` can be used for marking methods that remain to be implemented
  def someUnimplementedMethod(): String = ???

  def main(args: Array[String]): Unit = {

  }

}
