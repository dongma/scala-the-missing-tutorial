package org.lang.scala

/**
 * Nothing在scala中的使用，比较类似于Java中的Null
 *
 * @author Sam Ma
 * @date 2022/09/20
 */
object AboutNothing extends App {

  class MyPrecious // extends AnyRef

  // 1.Nothing
  def gimmeNumber(): Int = throw new NoSuchElementException

  def gimmeString(): String = throw new NoSuchElementException

  def gimmePrecious(): MyPrecious = throw new NoSuchElementException

  // 2.throw expressions return Nothing, Nothing != Unit != Null != anything at all
  // Nothing is the type of "nothingness"
  def gimmePrecious2(): MyPrecious = null

  // can you use Nothing?
  def aFunctionAboutNothing(a: Nothing): Int = 45

  def aFunctionReturningNothing(): Nothing = throw new RuntimeException


  // 3.Nothing is useful in generic, in covariant GENERICS
  abstract class MyList[+T] // if Dog extends Animal, then List[Dog] <: List[Animal]

  class NonEmptyList[+T](head: T, tail: MyList[T])


  // 4.使用EmptyList初始化MyList[String]、MyList[Integer]对象
  object EmptyList extends MyList[Nothing]

  val listOfString: MyList[String] = EmptyList
  val listOfIntegers: MyList[Integer] = EmptyList
  val preciousList: MyList[MyPrecious] = EmptyList

  def someUnimplementedMethod(): String = ???

}
