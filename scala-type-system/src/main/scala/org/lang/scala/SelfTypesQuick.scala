package org.lang.scala

/**
 * self types, really quick, http://rockthejvm.com/blog/self-types-quick
 *
 * @author Sam Ma
 * @date 2022/09/18
 */
object SelfTypesQuick {

  trait Edible
  // hierarchy #1
  trait Person {
    def hasAllergiesTo(thing: Edible): Boolean
  }
  trait Child extends Person
  trait Adult extends Person

  // hierarchy #2，Person与Diet兼容的问题，增加了一种constraint约束
  trait Diet { self: Person => // self type, whoever extends Diet Must also extend Person
    def eat(thing: Edible): Boolean = {
      if (self.hasAllergiesTo(thing)) false
      else 42 > 10
    }
  }
  trait Carnivore extends Diet with Person
  trait Vegetarian extends Diet with Person

  // PROBLEM: Diet must be applicable to Persons only，在不使用self-type前，必须继承Person类型
  // class VegetarianAthlete extends Vegetarian with Adult // enforce at compile error

  // Option #3，第三种实现方式，使用self-type类型约束Diet实现Person接口
  class VegetarianAthlete extends Vegetarian with Adult {
    override def hasAllergiesTo(thing: Edible): Boolean = false
  }

  // Option #1 - enforce a subtype relationship
  //  // hierarchy #1
  //  trait Person {
  //    def hasAllergiesTo(thing: Edible): Boolean
  //  }
  //  trait Child extends Person
  //  trait Adult extends Person
  //
  //  // hierarchy #2
  //  trait Diet extends Person {
  //    def eat(thing: Edible): Boolean = {
  //      if (this.hasAllergiesTo(thing)) false  // have access to the logic in the person class
  //      else 42 > 10
  //    }
  //  }
  //  trait Carnivore extends Diet with Person
  //  trait Vegetarian extends Diet with Person

  // Option #2 - add a type argument，通过generic type来进行控制
  //  trait Diet [+T <: Person] {
  //    def eat(thing: Edible): Boolean
  //  }
  //  trait Carnivore[T <: Person] extends Diet[T]
  //  trait Vegetarian[T <: Person] extends Diet[T]


  def main(args: Array[String]): Unit = {

  }
}
