package org.lang.scala

import scala.annotation.tailrec
import scala.util.control.TailCalls.TailRec
import scala.util.control.TailCalls._

/**
 * @author Sam Ma
 * @date 2020/06/13
 * scala中函数的内容，包括factorial尾部递归的定义以及collection创建etc
 */
object ScalaFunction {

  def main(args: Array[String]): Unit = {
    // 对与从1～10的序列找出其中的偶数，然后对偶数值乘以2 最后将list中的item进行reduce生成最终结果
    val reduceValue = (1 to 10) filter (_ % 2 == 0) map (_ * 2) reduce (_ * _)
    println(s"reduce value is: $reduceValue")

    // 使用递归函数factorial去计算函数值，在函数内部会使用递归进行数值计算
    val factorialValue = factorial(10)
    println(s"factorial(10) value: $factorialValue")

    // 在scala中当想要实现两个函数间相互调用时，可以使用TailRec这个尾递归调用对象，若funcA不匹配则转发到funcB上
    for (intValue <- 1 to 5) {
      val evenValue = isEven((1 to intValue).toList).result
      println(s"$intValue is even? $evenValue")
    }

    // 在scala中创建列表List，::操作符向队列的头部追加数据(向右结合)最终生成新的list
    val list1 = List("Programming", "Scala")
    val list2 = "People" :: "should" :: "read" :: list1
    // 使用++操作符号将两个list中的元素拼接起来，
    val list3 = List("People", "should", "read") ++ list1
    println(s"list2 value is: $list2, using ++ operate value: $list3")

    // 对于Seq序列其构造方法是+:操作符号，当你对伴随对象使用Seq.apply方法时，将创建出一个List(Vector与Seq操作完全类似)
    val shouldReadSeq = Seq("should", "read");
    val seqList = "People" +: "Programming" +: "Scala" +: shouldReadSeq
    // 在使用+:操作符号拼接Seq时，需要使用Seq.empty作为tail进行元素拼接
    val emptySeq = "People" +: "Programming" +: "Scala" +: Seq.empty
    println(s"seqList value is: $seqList, emptySeq value is: $emptySeq")

    // scala中的映射表(也即java中的Map对象)，使用map函数进行映射key值为capital名称 value为capital的length
    val stateCapitals = Map("Alabama" -> "Montgomery", "Alaska" -> "Juneau", "Wyoming" -> "Cheyenne")
    val lengths = stateCapitals map { keyValue => (keyValue._1, keyValue._2.length) }
    println(s"stateCapitals value: $stateCapitals, mapped lengths is: $lengths")
    // 将stateCapitals映射集合中的value值转换为大写形式，在向set中添加映射使用+进行拼接
    val upperCase = stateCapitals map { case (key, value) => (key, value.toUpperCase) }
    val stateCapitals2 = stateCapitals + ("Virginia" -> "Richmond")
    println(s"uppercase value: $upperCase, add Virginia capital value: $stateCapitals2")

    // scala中的Set集合(item元素不会出现重复)，向scala中Set集合添加元素时应该使用+符号
    val stateSet = Set("Alabama", "Alaska", "Wyonming")
    val setLengths = stateSet map (state => state.length)
    val stateSet2 = stateSet + ("New York", "Illinois")
    println(s"stateSet value: $stateSet, item length of stateSet: $setLengths, stateSet2 value: $stateSet2")

    // 对集合中元素使用foreach进行遍历，使用flatMap进行扁平映射(和java8中的语法类似)，使用filter进行数据过滤
    stateCapitals2 foreach { case (key, value) => println(s"$key" + ": " + value) }
    val stringList = List("now", "is", "", "the", "time")
    val flattenChars = stringList flatMap (string => string.toList)
    println(s"flattenChars value are: $flattenChars")

    // 找出Map集合中所有以"A"开头的映射集合的列表，使用reduce进行折叠 使用fold进行规约操作，fold需要两个参数
    val filterCapitals = stateCapitals filter { keyValue => keyValue._1 startsWith "A" }
    println(s"filterCapitals value is: $filterCapitals")

    val listValue = List(1, 2, 3, 4, 5, 6)
    val reduceResult = listValue reduce (_ + _)
    val foldValue = listValue.fold (10) (_ * _)
    println(s"listValue are $listValue, reduce result $reduceResult, foldValue is $foldValue")
  }

  /**
   * 定义尾部递归函数factorial，其使用嵌套的fact()方法完成了计算工作
   *
   * @param integer
   * @return
   */
  def factorial(integer: BigInt): BigInt = {
    @tailrec
    def fact(intValue: BigInt, accumulator: BigInt): BigInt =
      if (intValue == 1) accumulator
      else fact(intValue - 1, intValue * accumulator)

    fact(integer, 1)
  }

  def isEven(xs: List[Int]): TailRec[Boolean] =
    if (xs.isEmpty) done(true) else tailcall(isOdd(xs.tail))

  def isOdd(xs: List[Int]): TailRec[Boolean] =
    if (xs.isEmpty) done(false) else tailcall(isEven(xs.tail))

}
