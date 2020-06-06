package org.lang.scala

import org.lang.scala.BreedEnum.Weekday.Weekday

/**
 * @author Sam Ma
 * @date 2020/06/05
 * scala中的枚举类型
 */
object BreedEnum extends Enumeration {
  type BreedEnum = Value
  val doberman = Value("Doberman Pinscher")
  val yorkie = Value("Yorkshine Terrier")
  val scottie = Value("Scottish Terrier")
  val dane = Value("Great Dane")
  val portie = Value("Portuguese Water Dog")

  def main(args: Array[String]): Unit = {
    println("id \t breed")
    for (breed <- BreedEnum.values) println(s"${breed.id}\t $breed")

    // 通过自定义filter对BreedEnum.values中的数据进行过滤
    println("just terriers:")
    BreedEnum.values filter (_.toString.endsWith("Terrier")) foreach println

    println("Terrier Again??")
    BreedEnum.values filter isTerrier foreach println

    // 使用foreach从枚举类Weekday中按照isWorkingDay打印出工作日
    Weekday.values filter isWorkingDay foreach println

    // s"foo ${bar}"那么表达式bar会被转化为字符串并被插入到原字符串中的${bar}的位置
    val name = "Buck Trends"
    println(s"hello, $name")
  }

  def isTerrier(breedEnum: BreedEnum) = breedEnum.toString.endsWith("Terrier")

  object Weekday extends Enumeration {
    type Weekday = Value
    val Mon, Tue, Wed, Thu, Fir, Sat, Sun = Value
  }

  def isWorkingDay(day : Weekday) = ! (day == Weekday.Sat || day == Weekday.Sun)

}
