package org.lang.scala.rockjvm

/**
 * scala中类的定义、继承、伴生对象、trait、抽象类(Anonymous)等
 *
 * @author Sam Ma
 * @date 2024/03/24
 */
object ObjectOrientation extends App {

  // 1、class的定义及实例化，属性和方法
  class Animal {
    val age: Int = 0
    def eat() = println("I'm eating")
  }
  val animal = new Animal

  // 2、继承-构造函数的参数并不是fields，需在参数前面添加val才可成为属性字段
  class Dog(val name: String) extends Animal
  val aDog = new Dog("Lassie")
  aDog.name

  // 3、子类型多态，只有当aDeclaredAnimal调用eat时(runtime), 调用实际方法
  val aDeclaredAnimal: Animal = new Dog("Hachi")
  aDeclaredAnimal.eat()

  // 4、抽象类, 定义的属性by default public，可增加protected或private的约束
  abstract class WalkingAnimal {
    val hasLegs = true
    def walk(): Unit
  }

  // 5、trait接口，class只能单继承、实现多个接口
  trait Carnivore {
    def eat(animal: Animal): Unit
  }
  trait Philosopher {
    def ?!(thought: String): Unit
  }

  class Crocodile extends Animal with Carnivore with Philosopher {
    override def eat(animal: Animal): Unit = println("I am eating you, animal!")

    override def ?!(thought: String): Unit = println(s"I was thinking: $thought")
  }

  val aCroc = new Crocodile
  aCroc.eat(aDog)
  aCroc eat aDog  // 对象方法参数，only available for methods with ONE argument
  aCroc ?! "What if we could fly?"

  // 6、操作符在scala中是方法，两种写法功能相同
  val basicMath = 1 + 2
  val anotherBasicMath = 1.+(2)

  val dinosaur = new Carnivore {
    override def eat(animal: Animal): Unit = println("I am a dinosaur so I can eat pretty much anything")
  }
  /*
    Compiler实际操作为：
    class Carnivore_Anonymous_35728 extends Crocodile {
       override def eat(animal: Animal): Unit = println("I am a dinosaur so I can eat pretty much anything")
    }
    val dinosaur = new Carnivore_Anonymous_35728
   */

  // 7、单例对象，the only instance of the MySingleton type
  object MySingleton {
    val mySpecialValue = 53278
    def mySpecialMethod(): Int = 5327
    def apply(x: Int): Int = x + 1
  }
  MySingleton.mySpecialMethod()
  MySingleton.apply(65)
  MySingleton(65) // equivalent to MySingleton.apply(65)

  // 8、伴生对象，伴生对象可访问each other's private fields/methods
  object Animal {
    val canLiveIndefinitely = false
  }
  val animalsCanLiveForever = Animal.canLiveIndefinitely  // "static" fields/methods

  /*
   9、case class，轻量级的data structures with some boilerplate
    - sensible equals and hash code
    - serialization
    - companion with apply
    - pattern matching
  */
  case class Person(name: String, age:Int)
  val bob = Person("Bob", 54) // Person.apply("Bob", 54)

  // 10、exception，code can throw NullPointer exception
  try {
    val x: String = null
    x.length
  } catch { //  in Java: catch(Exception e) (...)
    case e: Exception => "some faulty error message"
  } finally {
    // execute some code no matter what
  }

  // 11、generics，using a generic with a concrete type
  abstract class MyList[T] {
    def head: T
    def tail: MyList[T]
  }
  val aList: List[Int] = List(1, 2, 3)  // List.apply(1, 2, 3)
  val first = aList.head
  val rest = aList.tail
  val aStringList = List("hello", "Scala")
  val firstString = aStringList.head  // string
  // Point #1: in Scala we usually operate with IMMUTABLE values/objects
  // Any modification to an object must return ANOTHER object
  /*
    Benefits:
    1) works miracles in multithreaded/distributed env
    2) helps making sense of the code ("reasoning object")
   */
  val reversedList = aList.reverse  // returns a NEW list
  // Point #2: Scala is closest to the OO ideal

}
