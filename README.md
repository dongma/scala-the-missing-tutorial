Scala: The Missing Tutorial
--------------

> Scala是基于java virtual  machine的一种语言，其广泛应用于诸多开源项目中，包括Apache Spark、Apache Kafka等。Martain Ordersky创造scala时，其运用了一些简洁的设计方法以及面向对象和函数式编程的一些看似简单却很强大的抽象，这使得Scala具备高聚合性。Scala是一门真正具备可扩展性的语言，我们既能使用它编写各种脚本语言，也能使用它实现大规模企业应用和中间件。

## Contributing to the Scala: The Missing Tutorial

Please contribute if you see an error or something that could be better! Raise an [issue]() or send me a pull request to improve. Contributions of all kinds, including corrections, additions, improvements, and translations, are welcome!

### 变量声明和执行流程控制
在scala中声明变量可使用`val`和`var`关键字，对于不可变的变量使用`val`进行声明（java中的`final`），对于可变引用使用`var`关键字，语句末尾不需使用`;`作为该语句结束的标志。
```scala
val array: Array[String] = new Array[String](5)
var stockPrice: Double = 100.0
```

方法声明使用`def funcName(variable1: Type=defaultValue, variable2:Type=defaultValue) = {function body}`这种方式，参数列表中的默认值是可选的，当不存在时可省略。

```scala
case class Point(x: Double = 0.0, y: Double = 0.0) {
  
  def shift(deltax: Double = 0.0, deltay: Double = 0.0) =
  copy(x + deltax, y + deltay)
}
```

scala中的偏函数`PartialFunction`：在偏函数中并不处理所有可能的输入，只处理那些与`case`语句匹配的函数输入。

```scala
val pf1: PartialFunction[Any, String] = {
  case s: String => "yes"
}
val pf2: PartialFunction[Any, String] = {
  case d: Double => "yes"
}
val pf = pf1 orElse pf2
// 10为int类型因其与便函数中的case语句不匹配，故不会输出到控制台
List("str", 3.14, 10) foreach {item => print(pf(item).toString + "")}
```

scala中的for推导式，`breed <- dogBreeds`为生成器表达式，其会将右边集合中逐个元素提取出来赋予`breed`变量，处理后结果可通过`yield`进行收集（类似于`java`8中 `stream().map().collect(Collectors.toList())`）。`item @ breed <- dogBreeds`会将每个元素赋予变量`item`，该变量可跨作用域在`yield`部分使用。

```scala
val dogBreeds = List("doberman", "yorkshire terrier", "dachshud", "scottish terrier", "greate dane",
                     "portuguese water dog")
val filteredBreeds = for {
  item @ breed <- dogBreeds
  if breed.contains("terrier") && breed.startsWith("yorkshire")
} yield breed
```

定义枚举类需继承`Enumeration`类，`type BreedEnum = Value`定义类型 之后用变量列表表示枚举变量值。 提取枚举值使用`BreedEnum.values`属性返回类型为集合 并通过`filter`对其值进行过滤，变量的惰性赋值可使用`lazy`关键字。

```scala
object BreedEnum extends Enumeration {
  type BreedEnum = Value
  val doberman = Value("Doberman Pinscher")
  val yorkie = Value("Yorkshine Terrier")
  val scottie = Value("Scottish Terrier")
  val dane = Value("Great Dane")
  val portie = Value("Portuguese Water Dog")
}
// 通过自定义filter对BreedEnum.values中的数据进行过滤
BreedEnum.values filter (_.toString.endsWith("Terrier")) foreach println

lazy val resource: Int= init()
```

scala中异常捕获的语法，使用`try..catch..finally`的语法处理异常行为，其与java中的异常处理语法类似。catch语句中`NonFatal(ex)`用于捕获所有非致命性异常，`finally`语句块多用于数据库、文件资源的释放。

```scala
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
```
### match模式匹配与隐式转换

`scala`中的模式匹配与`java`或者`C`语言中的`case`语句很相似，都为根据变量值寻求匹配的`case`条件并执行相应语句，`case`分支需覆盖数值的所有条件。`scala`中的`case`语句可匹配数值、变量、类型、序列`seq`、元素及`case`类的匹配。

```scala
for {
  x <- Seq(1, 2, 2.7, "one", "two", "four")
} {
  val value = x match {
    case 1 => "int 1"
    case i: Int => "other int: " + i
    case d: Double => "a double: " + d
    case "one" => "string one"
    case s: String => "other string: " + s
    case unexpected => "unexpected value: " + unexpected
  }
	println(value)
}
```

简单的`case`语句会使用变量`x`与`case`条件分支进行比较，`case 1`、`case i:Int`、`case d: Double`、`case "one"`会将`x`变量的类型及数值进行比较，若匹配则执行子表达式`=>`之后的语句。	

```scala
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
```

`case`类的匹配与简单`case`语句匹配类似，在`match`表达式中其会对变量`person`进行匹配（类型和数值）。`Person("Alice", 25, Address(_, "Chicago", _))`中匹配`person`的`name`为`Alice`、`age`为`25	`	，并且居住地在`Chicago`的变量。可使用`case p @ Person `对`case`表达式中匹配的变量进行绑定，在匹配表达中通过`$p`访问变量信息。

```scala
case p @ Person("Alice", 25, Address(_, "Chicago", _)) => println("Hi Alice! $p")
```

`case`类有一个伴随对象，伴随对象有一个名为`apply`的工厂方法，用于构造对象。可以推断一定还存在另一个自动生成的`unapply`方法，用于提取和解构对象。`case`类匹配时会将`Address`变量中的属性`street`和`country`进行抽取。

`scala`中隐式转换`implicit`的用法，使用隐式能够减少代码、能够向已有类型中注入新的方法（`implicit method`）。`calcTax`函数有两个参数列表`(amount: Float)`和隐式`(implicit rate: Float)`。使用`${calcTax(amount)}`调用`calcTax`函数，由于只传递了一个参数，`scala`则会从当前作用域中寻找类型匹配`implicit`变量`SimpleStateSalesTax.rate`作为参数。

```scala
def calcTax(amount: Float)(implicit rate: Float): Float = amount * rate
object SimpleStateSalesTax {
  implicit val rate: Float = 0.05F
}
{
  import SimpleStateSalesTax.rate
  val amount = 100F
  println(s"Tax on $amount = ${calcTax(amount)}")
}
```
### scala中的集合操作

不同语言有不同的核心数据结构，但大致都包含同一个子集，子集中包含列表（`list`）、向量（`vector`）等序列型集合，数组（`array`）、映射（`map`）和集合（`set`）。每种都支持一批无副作用的高阶函数，称为组合器（`combinar`），如`map`、`filter`、`fold`等函数。

在`scala`中使用`::`操作符向列表中追加元素，该元素会被追加到列表的头部，成为新列表的"头部"。除了头部，剩下的部分就是原列表中的元素，这些元素都没有被修改。`Nil`在`scala`中代表空队列，可以将`Nil`元素放到`Seq`的最后。`List`中还定义了`:+`和`+:`操作符，`:+`用于在尾部追加元素、`+:`方法用于在头部追加元素。

```scala
// scala中::向队列头部追加数据，从而创建新的列表. scala中以:结尾的方法向右结合，因此x::list调用其实是list.::(x),
// list2的计算结果如下: list1.::("read").::("should").::("People")
val list2 = "People" :: "should" :: "read" :: list1
// - all elements in list2 use :: operator are: [List(People, should, read, programming, scala)]
logger.info("all elements in list2 use :: operator are: [{}]", list2)

// 使用++能将两个list拼接起来, 拼接顺序与list的顺序相同 (将两个list->flatmap->整合元素)
val list4 = Seq("people", "should", "read") ++ list1
```

`scala`中对于`map`的操作，`key -> value`的形式语法形式实际上是用库中隐式转换实现的，实际上是调用了`Map.apply`方法。向`map`中添加元素可使用`+`操作符，移除元素使用`-`后跟`map`中`key`的列表（`stateCapitals2 - "Wyoming" - "Alabama"`）。

```scala
val stateCapitals = Map("Alabama" -> "Montgomery", "Alaska" -> "Juneau", "Wyoming" -> "Cheyenne")
val lengths = stateCapitals map { entry => (entry._1, entry._2.length) }
logger.info("static value length in stateCapitals map are: {}", lengths)
// 在向Map中添加entry时 也可使用+操作符操作，新的entry元素会被添加到Map中
val stateCapitals2 = stateCapitals + ("Virginia" -> "Richmond")
logger.info("use + operator to add entry to Map, value: {}", stateCapitals2)
// 使用-操作符从Map中根据key移除"Wyoming"、"Alabama"元素列表
val elements = stateCapitals2 - "Wyoming" - "Alabama"
logger.info("remove Wyoming、Alabama from stateCapitals2 value: {}", elements)
```

`scala`中的`Option`、`Some`和`None`操作符，其作用是对`java`中的`null`问题进行优化。注意：如果`Option`是一个`Some`，`Some.get`则会返回其中的值。然而，如果`Option`事实上是一个`None`值，`None.get`就会抛出一个`NoSuchElementException`的异常。一般使用时会用`get`的替代方案`getOrElse`获取数值。

```scala
// [main] INFO org.lang.scala.SeqOperate$ - Alabama: Some(Montgomery)
logger.info("Alabama: " + stateCapitals.get("Alabama"))
```

`Option`还用于在`case`表达式中核验数据是否存在，在`spark`中有用`Some`提取数据的用法。当`Some`判断存在数据时，执行`replicatedVertexView.withActiveSet(activeSet)`，`case Some`和`case None`覆盖了取值的两种方式。

```scala
val view = activeSetOpt match {
  case Some((activeSet, _)) =>
  replicatedVertexView.withActiveSet(activeSet)
  case None =>
  replicatedVertexView
}
val activeDirectionOpt = activeSetOpt.map(_._2)
```



