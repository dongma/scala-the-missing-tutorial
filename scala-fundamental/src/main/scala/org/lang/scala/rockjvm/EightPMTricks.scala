package org.lang.scala.rockjvm

/**
 * 8 PM Tricks you probably don't know:
 *
 * @author Sam Ma
 * @date 2022/09/17
 */
object EightPMTricks {

  // 1- switch on steroids, 用case匹配，返回ordinal索引下标
  val aNumber = 44
  val ordinal = aNumber match {
    case 1 => "first"
    case 2 => "second"
    case 3 => "third"
    case _ => aNumber + "th"
  }

  // 2- case class deconstruction
  case class Person(name: String, age: Int)

  val bob = Person("Bob", 34)
  val bobGreeting = bob match {
    case Person(name, age) => s"Hi, my name is $name and i am $age years old"
  }

  // trick #1, list extractors
  val numberList = List(1, 2, 3, 42)
  val mustHaveThree: String = numberList match {
    case List(_, _, 3, somethingElse) => s"List has 3rd element 3, so the 4th element is $somethingElse"
  }
  // trick #2, Haskell-like processing
  val startsWithOne: String = numberList match {
    case 1 :: tail => s"like starts with one, tail is $tail"
  }

  def process(aList: List[Int]): String = aList match {
    case Nil => s"List is empty"
    case head :: tail => s"list starts with $head, tail is $tail"
  }

  // trick #3, vararg pattern
  val dontCareAboutTheRest: String = numberList match {
    case List(_, 2, _*) => s"I only care about second number being 2"
  }
  // trick #4, other infix patterns
  val mustEndWithTheMeaningOfLife: String = numberList match {
    case List(1, 2, _) :+ 42 => "that's right, I have a meaning"
  }
  val mustEndWithTheMeaningOfLife2: String = numberList match {
    case List(1, _*) :+ 42 => s"I don't care how long the list is, I just want it end with 42"
  }

  // trick #5, type specifiers，根据类型match type
  def gimmeAValue(): Any = 45

  val gimmeTheType: String = gimmeAValue() match {
    case _: String => "I have a string"
    case _: Int => "I have a int"
    case _ => "Something else"
  }

  // trick #6 - name binding
  def requestInfo(p: Person): String = s"The person ${p.name} is a good person."

  val bobInfo: String = bob match {
    case p@Person(name, age) => s"$name's info: ${requestInfo(p)}"
  }

  // trick #7 -conditional guards, 根据条件进行自动匹配
  val ordinal2: String = aNumber match {
    case 1 => "first"
    case 2 => "second"
    case 3 => "third"
    case n if n % 10 == 1 => n + "st"
    case n if n % 10 == 2 => n + "nd"
    case n if n % 10 == 3 => n + "rd"
    case _ => aNumber + "th"
  }

  // trick #8 - alternative patterns，通过"|"符号用或的关系进行匹配
  val myOptionalList = numberList match {
    case List(1, _*) | List(_, _, 3, _*) => "I like this list"
    case _ => "I hate this list"
  }

  def main(args: Array[String]): Unit = {
    println(myOptionalList)
  }

}
