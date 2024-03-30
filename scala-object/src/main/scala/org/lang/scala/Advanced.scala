package org.lang.scala

import scala.concurrent.Future
import scala.util.{Failure, Success, Try}
import scala.concurrent.ExecutionContext.Implicits.global

/**
 * scala的一些高级语法，lazy延迟加载、Option和Some、try异常捕获、Thread线程、implicits
 *
 * @author Sam Ma
 * @date 2024/03/30
 */
object Advanced extends App {

  // lazy evaluation，lazy操作符 只有实际调用时，才会执行计算
  lazy val aLazyValue = 2
  lazy val lazyValueWithSideEffect = {
    println("I am so very lazy")
    43
  }
  val eagerValue = lazyValueWithSideEffect + 1
  // useful in infinite collections

  // "pseudo-collections: Option, Try"
  def methodWithCanReturnNull(): String = "hello, scala"
  val anOption = Option(methodWithCanReturnNull())  // Some("hello, scala")
  // option = "collection" which contains at most one element; Some(value) or None

  val stringProcessing = anOption match {
    case Some(string) => s"I have obtained a valid string: $string"
    case None => "I obtained nothing"
  }

  def methodWhichCanThrowException(): String = throw new RuntimeException
  val aTry = Try(methodWhichCanThrowException())
  // a try = "collection" with either a value if the code went well, or an exception if the code throw one

  val anotherStringProcessing = aTry match {
    case Success(validValue) =>  s"I have obtained a valid string: $validValue"
    case Failure(ex)  => s"I have obtained an exception: $ex"
  } // map、flatMap、filter

  /**
   * Evaluate something on another thread
   * (asynchronous programming)
   */
  val aFuture = Future({  // Future.apply
    println("Loading...")
    Thread.sleep(1000)
    println("I have computed a value.")
  })
  // future is a "collection" which contains a value when it's evaluated, future is
  // composable with map, flatMap and filter

  /**
   * Implicits basics
   */
  // #1: implicits arguments
  def aMethodWithImplicitArgs(implicit arg: Int) = arg + 1
  implicit val myImplicitInt = 46
  println(aMethodWithImplicitArgs)  // aMethodWithImplicitArgs(myImplicitInt)

  // #2: implicit conversions
  implicit class MyRichInteger(n: Int) {
    def isEven() = n % 2 == 0
  }
  println(23.isEven())  // new MyRichInteger(23).isEven()

}
