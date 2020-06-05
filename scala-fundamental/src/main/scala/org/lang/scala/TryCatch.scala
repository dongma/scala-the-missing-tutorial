package org.lang.scala

import scala.io.Source
import scala.util.control.NonFatal

/**
 * @author Sam Ma
 * @date 2020/06/05
 * scala中的try..catch语法，从命令行中根据文件枯井统计文件行数
 */
object TryCatch {
  /** usage: scala rounding.TryCatch filename1 filename2... */
  def main(args: Array[String]): Unit = {
    args foreach (arg => countLines(arg))
  }

  def countLines(filename: String) = {
    println()
    // 由于将source类声明为Option类型，因此我们在finally子句中能分辨出source对象是否是实例化
    var source: Option[Source] = None
    try {
      source = Some(Source.fromFile(filename))
      val size = source.get.getLines().size
      println(s"file $filename has $size lines")
    } catch {
      // scala捕获异常的方式更为紧凑，case NonFatal(ex)捕获所有非致命性异常
      case NonFatal(ex) => println(s"Non fatal exception! $ex")
    } finally {
      // <-操作实际用于从Option中获取value值，若source类型为None则不会发生任何事情
      for (s <- source) {
        println(s"Closing $filename..")
        s.close()
      }
    }
   }

}
