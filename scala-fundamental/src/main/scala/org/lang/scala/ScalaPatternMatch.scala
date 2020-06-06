package org.lang.scala

/**
 * @author Sam Ma
 * @date 2020/06/06
 * scala中的模式匹配：match中的值、变量和类型匹配、序列、元组、case类的匹配
 */
object scalaPatternMatch {

  def main(args: Array[String]): Unit = {
    for {
      // Seq序列元素包含不同的类型，因此序列的类型为Seq[Any]
      x <- Seq(1, 2, 2.7, "one", "two", "four")
    } {
      val string = x match {
        // 在case语句中当输入类型为Any时，在结尾用case_或者case some_name作为默认子句
        case 1 => "int 1"
        case i: Int => "other int: " + i
        case d: Double => "a double: " + d
        case "one" => "string one"
        case s: String => "other string: " + s
        case unexpected => "unexpected value: " + unexpected
      }
      println(string)

      // 使用反引号在case语句中根据请求参数在match语句中进行匹配
      checky(100)

      // scala中序列的匹配，包括Seq类型、Vector类型、Map类型
      val nonEmptySeq = Seq(1, 2, 3, 4, 5)
      val emptySeq = Seq.empty[Int]
      val nonEmptyList = List(1, 2, 3, 4, 5)
      val emptyList = Nil
      val nonEmptyVector = Vector(1, 2, 3, 4, 5)
      val emptyVector = Vector.empty[Int]
      val nonEmptyMap = Map("one" -> 1, "two" -> 2, "three" -> 3)
      val emptyMap = Map.empty[String, Int]

      for (seq <- Seq(
        nonEmptySeq, emptySeq, nonEmptyList, emptyList, nonEmptyVector, emptyVector,
        nonEmptyMap.toSeq, emptyMap.toSeq
      )) {
        println(seqToString(seq))
      }
    }
  }

  /**
   * 从方法入参中给定变量值，从Seq中查找参数值是否存在
   * @param y
   */
  def checky(y: Int) = {
    for {
      x <- Seq(99, 100, 101)
    } {
      val string = x match {
        // 在case语句中，以小写字母开头的标识符被认为时用来提取待匹配值的新变量，应使用反引号将其包围
        case `y` => "found y!"
        case i: Int => "int: " + i
      }
      println(string)
    }
  }

  /**
   * 定义递归方法，从Seq[T]中构造String，T为某种待定的类型 方法提是用来与输入的Seq[T]相匹配
   * @param seq
   * @tparam T
   * @return
   */
  def seqToString[T](seq: Seq[T]): String = seq match {
    // 用于匹配非空的Seq，提取其头部（第一个元素）以及尾部（除头部以外，剩下的元素）
    case head +: tail => s"$head +: " + seqToString(tail)
    case Nil => "Nil"
  }

}
