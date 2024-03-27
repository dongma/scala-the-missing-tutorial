package org.lang.scala.rockjvm

/**
 * RockTheJvm的函数式编程，apply方法、FUNCTION_X的描述、数据结构初步使用
 *
 * @author Sam Ma
 * @date 2024/03/27
 */
object FunctionalProgramming extends App {

  // Scala is OO
  class Person(name: String) {
    def apply(age: Int) = println(s"I have aged $age years")
  }

  val bob = new Person("Bob")
  bob.apply(43)
  bob(43) // Invoking bob as a function === bob.apply(43)

  /*
     Scala runs on the JVM, function programming，
     - compose functions
     - pass functions as args
     - return functions as results
     Conclusion: FunctionX= Function1, Function2,...Function22
   */
  val simpleIncrementer = new Function[Int, Int] {
    override def apply(arg: Int): Int = arg + 1
  }
  simpleIncrementer.apply(23) // 24
  simpleIncrementer(23) // simpleIncrementer.apply(23)
  // define a function, ALL SCALA FUNCTIONS ARE INSTANCES OF THESE FUNCTION_X TYPES

  // function with 2 arguments and a String return type
  val stringConcatenator = new Function2[String, String, String] {
    override def apply(arg1: String, arg2: String): String = arg1 + arg2
  }
  stringConcatenator("I love", "Scala")

  // syntax sugars
  val doubler: Int => Int = (x: Int) => 2 * x
  doubler(4) // 8
  /*
    equivalent to the much longer:
    val doubler: Function1[Int, Int] = new Function1[Int, Int] {
      override def apply(arg: Int): Int = 2 * arg
    }
   */

  // higher-order functions: take functions as args/return functions as results
  val aMappedList: List[Int] = List(1, 2, 3).map(x => x + 1)  // HOF
  val aFlatMappedList = List(1, 2, 3).flatMap { x =>
    List(x, 2 * x)
  } // alternative syntax, same as .map(x => list(x, 2 * x))
  val aFilteredList = List(1, 2, 3, 4, 5).filter(_ <= 3)  // equivalent to x => x <= 3

  // all pairs between the numbers 1, 2, 3 and the letters 'a', 'b', 'c'
  val allPairs = List(1, 2, 3).flatMap(number => List('a', 'b', 'c').map(letter => s"$number-$letter"))
  // for comprehensions，equivalent to the map/flatMap chain above
  val alternativePairs = for {
    number <- List(1, 2, 3)
    letter <- List('a', 'b', 'c')
  } yield s"$number-$letter"

  /**
   * collections
   */
  // lists
  val aList = List(1, 2, 3, 4, 5)
  val firstElement = aList.head
  val rest = aList.tail
  val aPrependedList = 0 :: aList // List(0, 1, 2, 3, 4, 5)
  val anExtendedList = 0 +: aList :+ 6 //  List(0, 1, 2, 3, 4, 5, 6)

  // sequences
  val aSequence: Seq[Int] = Seq(1, 2, 3) // Seq.apply(1, 2, 3)
  val accessedElement = aSequence(1) // the element at index 1: 2

  // vectors: fast Seq implementation
  val aVector = Vector(1, 2, 3, 4, 5)

  // Sets = no duplicate
  val aSet = Set(1, 2, 3, 4, 1, 2, 3) // set(1, 2, 3, 4)
  val setHas5 = aSet.contains(5) // false
  val anAddedSet = aSet + 5 // set(1, 2, 3, 4, 5)
  val aRemovedSet = aSet - 3 // set(1, 2, 4, 5)

  val aRange = 1 to 1000  // ranges
  val twoByTwo = aRange.map(x => 2 * x).toList  // List(2,4,6,8...2000)

  // tuples = groups of values under the same value
  val aTuple = ("Bon Jovi", "Rock", 1982)
  // maps，字典元素的不同写法
  val aPhonebook: Map[String, Int] = Map(
    ("Daniel", 6437812),
    "Jane" -> 327285
  )

}
