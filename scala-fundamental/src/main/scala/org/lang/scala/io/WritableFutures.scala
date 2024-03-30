package org.lang.scala.io

import scala.concurrent.{Future, Promise}

/**
 * scala WritableFutures实现异步编程序
 *
 * @author Sam Ma
 * @date 2022/09/19
 */
object WritableFutures {

  val aFuture = Future {
    42
  }

  // given - multithreaded
  object MyService {
    def produceAPreciousValue(theArg: Int): String = "The meaning of life is " + (theArg / 42)

    def submitTask[A](actualArg: A)(function: A => Unit): Boolean = {
      // run the function on actualArg at SOME POINT
      true
    }
  }

  // introducing Promises = "controller" of a future, step 1 - create a promise
  val myPromise = Promise[String]()
  // step 2 - extract the future
  val myFuture = myPromise.future
  // step 3 - consume the future
  val furtherPromising = myFuture.map(_.toUpperCase)

  def asyncCall(promise: Promise[String]): Unit = {
    promise.success("Your value here, your majesty")
  }
  // step 5 - call the producer
  asyncCall(myPromise)


  // futures are inherently non-deterministic, target
  def gimmeMyPreciousValue(yourArg: Int): Future[String] = {
    val thePromise = Promise[String]() // step 1 - create the promise
    // step 5
    MyService.submitTask(yourArg) { x: Int =>
      // step 4 - producer logic
      val preciousValue = MyService.produceAPreciousValue(x)
      thePromise.success(preciousValue)
    }
    thePromise.future // step 2: extract the future
  }

  def main(args: Array[String]): Unit = {

  }

}
