package org.lang.scala

import org.lang.scala.scalaPatternMatch.Op.Op

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

      // 对seqToString2进行重新的格式化，在每个元素中增加外部的括号，使用了两个case语句和递归就处理了序列
      for (seq <- Seq(nonEmptySeq, emptySeq, nonEmptyMap.toSeq)) {
        println(seqToString2(seq))
      }

      // 通过listToString方法将List元素列表转换成String字符串
      for (l <- List(nonEmptyList, emptyList)) {
        println(listToString(l))
      }

      // 可以通过序列化后的String反响构建List[Int]、listMap对象(List[(String, Int)])、map对象(Map[String, Int])
      val seqList = (1 +: (2 +: (3 +: (4 +: ( 5 +: Nil)))))
      val listMap = (("one", 1) +: (("two", 2) +: (("three", 3) +: Nil)))
      val mapObject = Map(listMap : _*)
      println(s"convert mapObject value: $mapObject")

      // 通过元素子棉量对scala中元组进行匹配，在java中并不存在元组 最多存在两纬的map
      val langs = Seq(
        ("Scala", "Martin", "Odersky"),
        ("Clojure", "Rich", "Mickey"),
        ("Lisp", "John", "McCarthy")
      )

      for (tuple <- langs) {
        tuple match {
          case ("Scala", _, _) => println("Found Scala")
          case (lang, first, last) => println(s"Found other language: $lang ($first, $last)")
        }
      }

      // case中的guard语句，在case语句中使用_代替变量名称
      for (i <- Seq(1, 2, 3, 4)) {
        i match {
          case _ if i%2 ==0 => println(s"even $i")
          case _ => println(s"odd: $i")
        }
      }

      // case类的匹配：可以在case语句中根据Person类成员字段属性值进行匹配
      val alice = Person("Alice", 25, Address("1 Scala Lane", "Chicago", "USA"))
      val bob = Person("Bob", 29, Address("2 Java Ave", "Miami", "USA"))
      val charlie = Person("Charlie", 32, Address("3 Python Ct", "Boston", "USA"))
      for (person <- Seq(alice, bob, charlie)) {
        person match {
          // p@...语法会将整个Person类的实例赋值给p, 当不需要从p的实例中取值只需写 p : Person => 就可以
          case p @ Person("Alice", 25, Address(_, "Chicago", _)) => println("Hi Alice! $p")
          case p @ Person("Bob", 29, a @ Address("2 Java Ave", "Miami", "USA")) =>
            println(s"hi ${p.name}! age {${p.age}}, in ${a.city}")
          case p @ Person(name, age, _) => println(s"Who are you, $age year-old person and $name? $p")
        }
      }
      // 对于(String, Double)组成的元组序列，表示商店里商品和价格 想通过for同时将其序列号打印出来，可使用zipWithIndex方法
      val itemCosts = Seq(("Pencil", 0.52), ("Paper", 1.35), ("Notebook", 2.43))
      val itemCostsIndices = itemCosts.zipWithIndex
      for (itemCostIndex <- itemCostsIndices) {
        itemCostIndex match {
          case ((item, cost), index) => println(s"$index: $item costs $cost each")
        }
      }

      // 匹配Seq中的元素序列，从最后一个元素Nil逆向访问所有元素列表 并最终转换为字符串
      for (seq <- Seq(nonEmptyList, nonEmptyVector, nonEmptyMap.toSeq)) {
        println(reverseSeqToString(seq))
      }

      // 使用滑动窗口windows()、windows()处理元素序列, 在提取序列时返回非固定数量的元素
      for (seq <- Seq(nonEmptyList, emptyList, nonEmptyMap.toSeq)) {
        println(windows(seq))
      }

      for (seq <- Seq(nonEmptyList, emptyList, nonEmptyMap.toSeq)) {
        println(windows2(seq))
      }

      import Op._
      val wheres = Seq(
        WhereIn("state", "IL", "CA", "VA"),
        WhereOp("state", EQ, "IL"),
        WhereOp("name", EQ, "Buck Trends"),
        WhereOp("age", GT, 29)
      )
      for (where <- wheres) {
        // where中匹配可变参数的语法形式为 name@_*, 声明时可变参数为vals: T*类型
        where match {
          case WhereIn(col, val1, vals@_*) =>
            val valStr = (val1 +: vals).mkString(",")
            println(s"WHERE $col in {$valStr}")
          case WhereOp(col, op, value) => println(s"Where $col $op $value")
          case _ => println(s"ERROR: Unknown expression: $where")
        }
      }

      // 对于不同的集合在scala中进行类型匹配，List(5.5, 5.6, 5.7), List("a", "b")匹配集合中首元素类型
      for {
        x <- Seq(List(5.5, 5.6, 5.7), List("a", "b"), Nil)
      } yield {
        x match {
          case seq: Seq[_] => println(s"seq ${doSeqMatch(seq)}", seq)
          case _ => println("unknown!", x)
        }
      }

      // 模式匹配的其它用法，使用这种方式直接将Person中对象的属性提取出来
      val Person(name, age, Address(_, state, _)) = Person("Dean", 29, Address("1 scala way", "CA", "USA"));
      println(s"name: $name, age: $age, Address.state: $state")

      // 将List中的元素分成head和tail两部分，head1为序列的首元素、tail为剩余的元素序列
      val head1 +: tail = List(1, 2, 3)
      println(s"seq list head1 value: $head1, tail value: $tail")

      val (sum, count) = sum_count(List(1, 2, 3, 4, 5))
      println(s"sum value: $sum count value of List is: $count")
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

  def seqToString2[T](seq: Seq[T]): String = seq match {
    // 重新进行了格式化，增加了外部的括号"()"
    case head +: tail => s"($head +: ${seqToString2(tail)})"
    case Nil => "(Nil)"
  }

  def listToString[T](list: List[T]): String = list match {
    // 使用::替代了+:符号，将List的head和tail内容拼接起来
    case head :: tail => s"($head :: ${listToString(tail)})"
    case Nil => "(Nil)"
  }

  // Simplistic address type. Using all strings is questionable, too
  case class Address(street: String, city: String, country: String)
  case class Person(name: String, age: Int, address : Address)

  def reverseSeqToString[T](l: Seq[T]): String = l match {
    case prefix :+ end => reverseSeqToString(prefix) + s" :+ $end"
    case Nil => "Nil"
  }

  def windows[T](seq: Seq[T]): String = seq match {
    // 提取Seq中的前两个元素，用_*表示其它剩余的元素(可变参数列表)
    case Seq(head1, head2, _*) => s"($head1, $head2), " + windows(seq.tail)
    // 还需要匹配Seq中只有一个元素的序列，当最后只剩余Nil时 对其进行直接拼接
    case Seq(head, _*) => s"($head, _), " + windows(seq.tail)
    case Nil => "Nil"
  }

  def windows2[T](seq: Seq[T]): String = seq match {
    case head1 +: head2 +: tail => s"($head1, $head2), " + windows2(seq.tail)
    case head +: tail => s"($head, _), " + windows2(tail)
    case Nil => "Nil"
  }

  object Op extends Enumeration {
    type Op = Value

    val EQ = Value("=")
    val NE = Value("!=")
    val LTGT = Value("<>")
    val LT = Value("<")
    val LE = Value("<=")
    val GT = Value(">")
    val GE = Value(">=")
  }

  case class WhereOp[T](columnName: String, op: Op, value: T)

  case class WhereIn[T](columnName: String, val1: T, vals: T*)

  // 对集合序列进行匹配，由于jvm的类型擦除并不能区分匹配List("a", "b") Seq(List(5, 5, 6, 7))
  def doSeqMatch[T](seq: Seq[T]): String = seq match {
    case Nil => "Nothing"
    case head +: _ => head match {
      case _: Double => "Double"
      case _: String => "String"
      case _ => "Unmatched seq element"
    }
  }

  def sum_count(ints: Seq[Int]) = (ints.sum, ints.size)

}
