package org.lang.scala.rockjvm

import java.io.File
import java.util.Scanner

import org.apache.commons.io.FileUtils

import scala.io.Source

/**
 * 用scala读取file中文件的内容
 *
 * @author Sam Ma
 * @date 2022/09/20
 */
object ReadFiles {

  val filePath = "/Users/madong/datahub-repository/scala-the-missing-tutorial/README.md"

  // version 1 - The Java Way, 使用Scanner、File class进行读取
  val file = new File(filePath)
  val scanner = new Scanner(file)
  /*while (scanner.hasNextLine) {
    val line = scanner.nextLine()
    println(line)
  }*/

  // version 2 - The Java Way with cheats, Apache common-io
  val fileContents = FileUtils.readLines(file)
//  fileContents.forEach(println)

  // version 3 - The scala way
  val scalaFileContents: Iterator[String] = Source.fromFile(file).getLines
//  scalaFileContents.foreach(println)

  // version 4 - The Boss read，directly call open(path).read，RichFile Class用了隐式转换
  def open(path: String) = new File(path)
  implicit class RichFile(file: File) {
    def read() = Source.fromFile(file).getLines
  }

  val readLikeBoss = open(filePath).read() // new RichFile(open(file)).read()
  readLikeBoss.foreach(println)

  def main(args: Array[String]): Unit = {

  }

}
