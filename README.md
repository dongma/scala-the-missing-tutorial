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



