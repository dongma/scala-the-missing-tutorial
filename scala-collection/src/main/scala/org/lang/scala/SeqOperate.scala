package org.lang.scala

import org.slf4j.{Logger, LoggerFactory}

/**
 * @author Sam Ma
 * @date 2020/10/18
 * scala中一些一些数据结构常见的用法Seq、Vector和Map等
 */
object SeqOperate {

  private[this] val logger: Logger = LoggerFactory.getLogger(SeqOperate.getClass)

  def main(args: Array[String]): Unit = {
    // List在构造函数中可通过传递element元素列表 创建list1对象
    val list1 = List("programming", "scala")
    logger.info("list1 value: {}", list1)

    // scala中::向队列头部追加数据，从而创建新的列表. scala中以:结尾的方法向右结合，因此x::list调用其实是list.::(x),
    // list2的计算结果如下: list1.::("read").::("should").::("People")
    val list2 = "People" :: "should" :: "read" :: list1
    // - all elements in list2 use :: operator are: [List(People, should, read, programming, scala)]
    logger.info("all elements in list2 use :: operator are: [{}]", list2)

    // 使用::向空队列Nil中追加元素, 实际调用Nil.::("Scala").::("Programming"), 最终结果为("Programming", "Scala")
    val list3 = "Programming" :: "Scala" :: Nil
    logger.info("insert elements to Nil Seq, elements are: [{}]", list3)

    // 使用++能将两个list拼接起来, 拼接顺序与list的顺序相同 (将两个list->flatmap->整合元素)
    val list4 = Seq("people", "should", "read") ++ list1
    logger.info("use ++ operator to two list, value are: [{}]", list4)

    // list中还定义了:+和+:操作符，:+用于在尾部追加元素、+:方法用于在头部追加元素
    val seq1 = list1 :+ "Rocks!"
    logger.info("use :+ operator to concat element: {}", seq1)
    /*
     * scala中还定义了Vector类型：Vector的所有操作都是O(1)，而list对于那些需要访问头部以外元素的操作，都需要O(n)操作
     */
    val vector = "people" +: "should" +: "read" +: Vector.empty
    logger.info("use +: to add elements to vector, elements are: {}", vector)

    /*
     * scala中映射表Map的常用操作, 实例化Map集合通过key->value方式初始化
     */
    val stateCapitals = Map("Alabama" -> "Montgomery", "Alaska" -> "Juneau", "Wyoming" -> "Cheyenne")
    val lengths = stateCapitals map { entry => (entry._1, entry._2.length) }
    logger.info("static value length in stateCapitals map are: {}", lengths)
    // 在向Map中添加entry时 也可使用+操作符操作，新的entry元素会被添加到Map中
    val stateCapitals2 = stateCapitals + ("Virginia" -> "Richmond")
    logger.info("use + operator to add entry to Map, value: {}", stateCapitals2)
    // 使用-操作符从Map中根据key移除"Wyoming"、"Alabama"元素列表
    val elements = stateCapitals2 - "Wyoming" - "Alabama"
    logger.info("remove Wyoming、Alabama from stateCapitals2 value: {}", elements)

    /*
     * scala中fold()和reduce()函数, reduce使用(_ + _)对elements中的元素累加、fold(init)(operator)执行初始值和operator计算
     */
    val listElements = List(1, 2, 3, 4)
    val reduce = listElements reduce (_ + _)
    val fold = listElements.fold(10) (_ * _)
    logger.info("reduce element in listElements is [{}], foldValue is: [{}]", reduce, fold)

    /*
     * scala中的Option、Some和None操作符. Map.get方法返回Option[T]
     */
    // [main] INFO org.lang.scala.SeqOperate$ - Alabama: Some(Montgomery)
    logger.info("Alabama: " + stateCapitals.get("Alabama"))
  }

}
