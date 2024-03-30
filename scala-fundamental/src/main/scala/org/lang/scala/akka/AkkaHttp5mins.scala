package org.lang.scala.akka

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model._
import akka.stream.ActorMaterializer

import java.net.URLEncoder
import scala.concurrent.Future

/**
 * 使用Akka-http组件来发送http请求，用akka-http组件发请求
 *
 * @author Sam Ma
 * @date 2022/09/19
 */
object AkkaHttp5mins {

  implicit val system = ActorSystem() // Akka actors
  implicit val materializer = ActorMaterializer() // Akka streams

  import system.dispatcher // "thread pool"

  val source =
    """
      |object SimpleApp {
      | val aField = 2
      |
      | def aMethod(x: Int) = x + 1
      |
      | def main(args: Array[String]):Unit = println(aField)
      |}
      |""".stripMargin

  val request = HttpRequest(
    method = HttpMethods.POST,
    uri = "http://markup.su/api/highlighter",
    entity = HttpEntity(
      ContentTypes.`application/x-www-form-urlencoded`, // application/json in most cases
      s"source=${URLEncoder.encode(source, "UTF-8")}&language=Scala&theme=Sunburst" // the actual data you want to send
    )
  )

  /**
   * Http#singleRequest组织payload参数，然后进行请求的发送
   *
   * @return
   */
  def sendRequest(): Future[String] = {
    val responseFuture: Future[HttpResponse] = Http().singleRequest(request)
    val entityFuture = responseFuture.flatMap(response => response.entity.toStrict(2.seconds))
    entityFuture.map(entity => entity.data.utf8String)
  }

  def main(args: Array[String]): Unit = {
    sendRequest().foreach(println)
  }

}
