package org.lang.scala.rockjvm

/**
 * Scala中抽象类与Traits接口的区别
 *
 * @author Sam Ma
 * @date 2022/09/26
 */
object AbstractClassesVsTraits {

  class Animal
  class Dog extends Animal

  /* - they can't be instantiated on their own
   * - may have abstract fields/methods
   * - may have non-abstract fields/methods
   */
  abstract class Person {
    val canFly: Boolean = false
    val canDrive: Boolean
    def discussWith(another: Person): String
  }

  trait PersonTrait {
    val canFly: Boolean = false
    val canDrive: Boolean
    def discussWith(another: Person): String
  }

  // if you're extending a SINGLE type, abstract classes have very little difference traits
  class Adult(val name: String, hasDrivingLicence: Boolean) extends PersonTrait {
    override val canDrive: Boolean = hasDrivingLicence
    override def discussWith(another: Person): String = s"Indeed, $another, Kant was such a revolution in philosophy"
  }

  /**
   * Difference #1:
   * - can inherit from a single abstract class
   * - can inherit from multiple traits
   */
  abstract class Pet(name: String)
//  trait PetTrait(name: String)

  /**
   * Difference #2:
   * - abstract classes can take constructor args
   * - traits can't ... unitl scala 3
   */

  /**
   * Difference #3, Represent:
   * "things" are classes, "behaviors" as traits
   */

  def main(args: Array[String]): Unit = {

  }

}
