package org.lang.scala

import org.slf4j.{Logger, LoggerFactory}

/**
 * @author Sam Ma
 * @date 2020/06/26
 * 深入理解scala中的for表达式，for表达式yield与map对应 表达式生成器提取元素
 */
object ForInExpr {

  private val logger: Logger = LoggerFactory.getLogger(this.getClass)

  def main(args: Array[String]): Unit = {
    val states = List("Alabama", "Alaska", "Virginia", "Wyoming")
    val upperCase = for {
      state <- states
    } yield state.toUpperCase
    logger.info(s"states uppercase value is: {}", joinOutputItems(upperCase))
    // 在for循环中对item元素进行uppercase，其最终结果是与map 函数类似
    val mapValue = states map (_.toUpperCase)
    logger.info(s"uppercase item in Seq use map to convert: {}", joinOutputItems(mapValue))

    // 在for表达式中使用两次<-表达式生成器时，其最终的效果会与flatMap一致 将item集合拍平 将集合中元素归集起来
    val flatMapValue = for {
      state <- states
      char <- state
    } yield s"$char-${char.toUpper}"
    logger.info(s"use extract operator two times: {}", joinOutputItems(flatMapValue))

    val flatMapStates = states flatMap (_.toSeq map (char => s"$char-${char.toUpper}"))
    logger.info(s"use flatMap to get all character: {}", flatMapStates)

    // 若要在for表达式中对元素进行过滤，可使用guard表达式 其与withFilter()函数功能一致
    val filterUpperChar = for {
      state <- states
      char <- state
      if char.isLower
    } yield s"$char-${char.toUpper}"
    logger.info(s"use guard expression to filter upper char: {}", joinOutputItems(filterUpperChar))
    val withFilterChar = states flatMap (_.toSeq withFilter(_.isLower) map (char => s"$char-${char.toUpper}"))
    logger.info(s"use withFilter() method to filter upper char: {}", joinOutputItems(withFilterChar))

    /*
     * 在for语句中使用 Some(i)<-list语句会进行模式匹配，移除None元素 并抽取类型为Some的元素的整数值
     */
    val results: Seq[Option[Int]] = Vector(Some(10), None, Some(20))
    val multiple = for {Some(value) <- results} yield (2 * value)
    logger.info(s"Seq result multiple 2 value: {}", joinOutputItems(multiple))
  }

  /**
   * 将Seq[String]中的元素打印到控制台上
   *
   * @param collect
   */
  def joinOutputItems(collect: Seq[Any]): String = {
    var itemList = "["
    for (item <- collect) itemList += (item + ", ")
    val toString = itemList.substring(0, itemList.length - 2) + "]"
    toString
  }

}
