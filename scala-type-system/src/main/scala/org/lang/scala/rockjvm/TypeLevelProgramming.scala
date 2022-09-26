package org.lang.scala.rockjvm

/**
 * scala type类型的编程，通过implicit定义隐式转换
 *
 * @author Sam Ma
 * @date 2022/09/26
 */
object TypeLevelProgramming {

  // boilerplate
  import scala.reflect.runtime.universe._
  def show[T](value: T)(implicit tag: TypeTag[T]) = tag.toString()
    .replace("org.lang.scala.rockjvm.TypeLevelProgramming", "")

  // type-level programming, Peano arithmetic
  trait Nat
  class _0 extends Nat
  class Succ[N <: Nat] extends Nat

  type _1 = Succ[_0]
  type _2 = Succ[_1]  // Succ[Succ[_0]]
  type _3 = Succ[_2]
  type _4 = Succ[_3]

  // _2 < _4，定义<符号 用于比较_A与_B对象
  trait <[A <: Nat, B <: Nat]
  object < {
    implicit def ltBasic[B <: Nat]: <[_0, Succ[B]] = new <[_0, Succ[B]] {}
    implicit def inductive[A <: Nat, B <: Nat](implicit lt: <[A, B]): <[Succ[A], Succ[B]] = new <[Succ[A], Succ[B]] {}
    def apply[A <: Nat, B <: Nat](implicit lt: <[A, B]) = lt
  }
  // val invalidComparison: _3 < _2 = <[_3, _2] - will not compile: 3 is Not less than 2
  val comparison: _1 < _3 = <[_1, _3]
  /*
  <.apply[_1, _3] -> requires implicit <[_1, _3]
   * inductive[_1, _3] -> requires implicit <[_0, _2]
   * ltBasic[_1] -> produces implicit <[_0, Succ[_1]] == <[_0, _2]
   */

  trait <=[A <: Nat, B <: Nat]
  object <= {
    implicit def lteBasic[B <: Nat]: <=[_0, B] = new <=[_0, B] {}
    implicit def inductive[A <: Nat, B <: Nat](implicit lte: <=[A, B]): <=[Succ[A], Succ[B]] = new <=[Succ[A], Succ[B]] {}
    def apply[A <: Nat, B <: Nat](implicit lte: <=[A, B]) = lte
  }

  val lteTest: _1 <= _1 = <=[_1, _1]
//  val invalidLte: _3<=_1 = <=[_3, _1]  - will not compile

  def main(args: Array[String]): Unit = {
    println(show(List(1, 2, 3)))
  }

}
