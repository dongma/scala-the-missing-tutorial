package org.lang.scala.rockjvm


import akka.actor.typed.ActorSystem
import akka.actor.typed.scaladsl.Behaviors

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.{Future, Promise}

/**
 * Async Not Blocking
 *
 * @author Sam Ma
 * @date 2022/09/22
 */
object AsyncNonBlocking {

  def blockingFunction(x: Int): Int = {
    Thread.sleep(10000)
    x + 42
  }
  blockingFunction(5) // blocking all
  val meaningOfLife = 42 // will wait 10 seconds before evaluating

  // asynchronous blocking call, return Future[Int] type
  def asyncBlockingFunction(x: Int): Future[Int] = Future {
    Thread.sleep(10000)
    x + 42
  }
  asyncBlockingFunction(5)
  val anotherMeaningOfLife = 43 // evaluates immediately

  // asynchronous, NON-blocking
  def createSimpleActor() = Behaviors.receiveMessage[String] { someMessage =>
    println(s"Received a message: $someMessage")
    Behaviors.same
  }
  val rootActor = ActorSystem(createSimpleActor(), "TestSystem")
  rootActor ! "Message in a bottle" // enqueuing a message, asynchronous NON-BLOCKING

  // Define Promise Resolver in ActorSystem
  val promiseResolver = ActorSystem(
    Behaviors.receiveMessage[(String, Promise[Int])] {
      case (message, promise) =>
        promise.success(message.length) // do some computations
        Behaviors.same
    },
    "promiseResolver"
  )
  def doAsyncNonBlockingComputation(s: String): Future[Int] = {
    val aPromise = Promise[Int]()
    promiseResolver ! (s, aPromise)
    aPromise.future
  }

  val asyncNonBlockingResult = doAsyncNonBlockingComputation("Some message")  // Future[Int]- async, non-blocking
  asyncNonBlockingResult.onComplete(println)

  def main(args: Array[String]): Unit = {

  }

}
