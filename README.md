Scala: The Missing Tutorial
--------------

Scala是基于java virtual  machine的一种语言，其广泛应用于诸多开源项目中，包括Apache Spark、Apache Kafka等。Martain Ordersky创造scala时，其运用了一些简洁的设计方法以及面向对象和函数式编程的一些看似简单却很强大的抽象，这使得Scala具备高聚合性。Scala是一门真正具备可扩展性的语言，我们既能使用它编写各种脚本语言，也能使用它实现大规模企业应用和中间件。

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

