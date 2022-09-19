package org.lang.scala.rockjvm

/**
 * scala中Loops的写法，区别与java中的for、while等
 *
 * @author Sam Ma
 * @date 2022/09/19
 */
object Loop {

  val x = 3 // constant, x的值不能改变，否则会发生compile error

  var y = 5
  y = 999 // ok, imperatively = INSTRUCTIONS
  while (y < 9999) {  // don't use it - just trust me
    println("Hey ma, I'm looping")
    y += 1
  }

  val result = (999 to 9999).foreach(_ => println("Hey ma, I'm doing it right"))
  // transform a list
  List(1, 2, 3).map(x => x + 1) // List(2, 3, 4)
  List(1, 2, 3).flatMap(n => Seq.fill(n)(1)) // List(1, 1,1, 1,1,1, 1,1,1,1)
  List(1, 2, 3).filter(n => n % 42 == 0) // fold, find, count, maxBy, sum, reduce

  // newb = "how can I loop through this list?"
  // mature = "how can I TRANSFORM this into what I want?", foreach fallacy
  List(1, 2, 3).foreach(x =>  // lambda = function object
    println(x)
  )

  /*
   List<Integer> myList = ...
   for (x : myList) {
    System.out.println(x)
   } */
  // for comprehensions = EXPRESSIONS
  val pairs = for {
    x <- List(1, 2, 3)
    y <- List(4, 5, 6)
  } yield (x, y)

  // equivalent code = for comprehension compiles to THIS
  List(1, 2, 3).flatMap(xe => List(4, 5, 6).map(y => (xe, y)))

  def main(args: Array[String]): Unit = {

  }

}
