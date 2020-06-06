package org.lang.scala

/**
 * @author Sam Ma
 * @date 2020/06/06
 * 在scala中定义抽象需用到trait关键字，可将其视为允许将声明方法实现的接口
 */
object ScalaTrait {

  def main(args: Array[String]): Unit = {
    val service1 = new ServiceImportante("uno")
    (1 to 3) foreach (i => println(s"Result: ${service1.work(i)}"))

    // 声明一个混入trait 加入了日志功能的服务，在调用父类work方法同时打印日志 (只混入了一个实例Logging)
    val service2 = new ServiceImportante("dos") with StdoutLogging {
      override def work(i: Int): Int = {
        info(s"Starting work: i = $i")
        val result = super.work(i)
        info(s"Ending work: i = $i, result = $result")
        result
      }
    }
    (1 to 3) foreach (i => println(s"Result: ${service2.work(i)}"))

    // 若是希望在ServiceImportante的多个实例中混入StdoutLogging特征，我们可以声明一个新类
    class LoggedServiceImportante(name: String) extends ServiceImportante(name) with StdoutLogging {
      // define override method
    }

  }

  class ServiceImportante(name: String) {
    def work(i: Int): Int = {
      println(s"ServiceImportante: Doing important work! $i")
      i + 1
    }
  }

  // 定义日志抽象类Logging，及一个日志抽象的实现 用于将日志信息输出到标准输出
  trait Logging {
    def info(message: String): Unit

    def warning(message: String): Unit

    def error(message: String): Unit
  }

  trait StdoutLogging extends Logging {
    def info(message: String) = println(s"Info: $message")

    def warning(message: String) = println(s"Warning: $message")

    def error(message: String) = println(s"error: $message")
  }

}
