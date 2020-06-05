package org.lang.scala

import java.io.File
import java.util.Calendar

/**
 * @author Sam Ma
 * @date 2020/06/04
 * scala语法的核心要点，操作符重载、无参数方法、dsl等
 */
object ScalaCoreSyntax {

  def main(args: Array[String]): Unit = {
    // scala中的无参数方法：当定义无参数方法时省略了括号，那么在调用这些方法时必须省略括号，定于无副作用的无参方法
    // 时省略括号（scala社区约定），如下方的将1～4之间的偶数打印出来
    def isEven(n: Int) = (n % 2) == 0

    // 通过无参数方法简化数据过滤的过程，filter isEven foreach都可省略调用括号
    List(1, 2, 3, 4).filter((i: Int) => isEven(i)).foreach((i: Int) => println(i))
    List(1, 2, 3, 4).filter((i: Int) => isEven(i)).foreach(i => println(i))
    List(1, 2, 3, 4).filter(isEven).foreach((i: Int) => println(i))
    List(1, 2, 3, 4) filter isEven foreach println

    // 在scala中所有以:结尾的方法都与右边的对象所绑定，其他方法则是左绑定. 可通过::方法将某一元素放到列表前面，
    // 这一操作成为成为称为cons操作，cons是constructor的缩写
    val list = List('a', 'b', 'c')
    println(":: operator list content: " + 'a' :: list)

    // scala中的if语句，scala中的if语句的每个分支支持返回值，scala的if语句为表达式 像java中的三目运算符对于scala是多余的
    val configFile = new File("somefile.txt")
    val configFilePath = if (configFile.exists()) {
      configFile.getAbsolutePath
    } else {
      configFile.createNewFile()
      configFile.getAbsoluteFile()
    }
    println("configFile absolute path: " + configFile.getAbsolutePath)
    configFile.delete

    // scala中的for循环，将dogBreeds中的元素打印出来，其并不返回任何值 与java中的for循环较为类似
    val dogBreeds = List("doberman", "yorkshire terrier", "dachshud", "scottish terrier", "greate dane",
      "portuguese water dog")
    for (breed <- dogBreeds) println(breed + " ")

    // 与breed <- dogBreeds内容类似，此类型表达式称为生成器表达式(generator expression)
    for (i <- 1 to 10) print(i + " ")

    // 保护式筛选元素 可通过if表达式来筛选出我们希望保留的元素，这种表达式也称为保护式guard
    for (breed <- dogBreeds if breed.contains("terrier") && !breed.startsWith("yorkshire")) print("[" + breed + "] ")

    // 当不需要打印过滤后的集合，只需对过滤后集合中元素进行收集时，则可以使用yield(随着代码的执行，过滤后结果会被累加起来)
    val filteredBreeds = for {
      breed <- dogBreeds
      if breed.contains("terrier") && breed.startsWith("yorkshire")
    } yield breed
    println("filteredBreeds collections: " + filteredBreeds)

    // Option、Some和None，避免使用null值 Map.get()方法返回了Option[T]类型，T可能是实际的值或者null
    val stateCapitals = Map(
      "Alabama" -> "Montgomery",
      "Alaska" -> "Juneau",
      "Myoming" -> "Cheyenne"
    )
    println("get the capitals warpped in Options, Alabama: " + stateCapitals.get("Alabama") + " ," +
      " Alaska: " + stateCapitals.get("Alaska") + " ,Myoming: " + stateCapitals.get("Myoming"))
    println("get the capitals themselves out of the Options: " + stateCapitals.get("Alabama").get + " ," +
      " Alaska: " + stateCapitals.get("Alaska").getOrElse("Oops!") + " ,Myoming: " + stateCapitals.get("unknown").getOrElse("Oops2!"))

    // 扩展作用域与值定义，使用Option类型对象，在for循环中第一个表达式均返回Option对象，而后续的代码则继续使用<-提取Option中的值
    val dogBreedsOptional = List(Some("doberman"), None, Some("yorkshire terrier"), Some("dachshund"), None,
      Some("scottish terrier"), None, Some("Great Dane"), Some("protuguese water dog"))
    println("first pass of dogBreedsOptional: ")
    for {
      breedOption <- dogBreedsOptional
      // 当从None中提取对象时，不会有异常进行抛出，此item元素会被忽略
      breed <- breedOption
      upcasedBreed = breed.toUpperCase
    } print(upcasedBreed + " ")

    println("second pass of dogBreedsOptional: ")
    for {
      // 此for表达式使用了模式匹配，只有当breedOption为Some类型时，表达式Some(breed) <- dogBreedsOptional才会成功提取出breed
      // 所有操作一步完成，None元素不再被处理
      Some(breed) <- dogBreedsOptional
      // 当遍历某一集合或其他像Option这样的容器并试图提取值时，应使用<-箭头符号。当执行并不需要迭代的赋值操作时 就应该使用=
      upcasedBreed = breed.toUpperCase
    } print(upcasedBreed + " ")

    // while循环和do-while循环在scala语言中，而do while循环的写法与C和Java没有太大的区别
    /*while (!isFridayThirteen(Calendar.getInstance())) {
      println("Today isn't Firday the 13th. Lane.")
      Thread.sleep(86400000)
    }*/


  }

  /**
   * 根据Calendar对象判断日期是否为周五、同时是否为年的最后一月
   *
   * @param cal
   * @return
   */
  def isFridayThirteen(cal: Calendar): Boolean = {
    val dayOfWeek = cal.get(Calendar.DAY_OF_WEEK)
    val dayOfMonth = cal.get(Calendar.DAY_OF_MONTH)
    // scala将最后一个表达式的结果值作为该方法的返回结果
    (dayOfWeek == Calendar.FRIDAY) && (dayOfMonth == 13)
  }

}
