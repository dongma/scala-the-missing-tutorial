package org.lang.scala

import scala.util.control.NonFatal

/**
 * @author Sam Ma
 * @date 2020/06/05
 * 使用SOC将关注点分离，用scala工具构建小型领域特定语言(DSL)
 */
object manage {
  /**
   * R <: {def close(): Unit}: R表示我们要管理的资源类型，<:则意味着R属于其他人类型的子类型 (此处可理解为Closable接口)
   * T : 用于处理资源的匿名函数
   * (resource: => R): resource实际上是一个传名参数，暂且将其视为一个在调用时应省略括号的函数
   * (f: R => T): 一个输入为resource、返回值类型为T的匿名函数，该匿名函数将负责处理resource资源
   * */
  def apply[R <: {def close(): Unit}, T](resource: => R)(f: R => T) = {
    var res: Option[R] = None
    try {
      // Source.fromFile(fileName)方法直到调用Some(resource)时才会被正式调用
      res = Some(resource)
      f(res.get)
    } catch {
      case NonFatal(ex) => println(s"Non fatal exception! $ex")
    } finally {
      if (res != None) {
        println(s"Closing resource...")
        res.get.close()
      }
    }
  }
}

object TryCatchARM {
  def main(args: Array[String]): Unit = {
    /** usage: scala rounding.TryCatch filename1 filename2... */
    args foreach (arg => countLines(arg))
  }

  import scala.io.Source

  def countLines(fileName: String) = {
    manage(Source.fromFile(fileName)) { source =>
      val size = source.getLines().size
      println(s"file $fileName has $size lines")
      if (size > 20) throw new RuntimeException("Big File!")
    }
  }

}
