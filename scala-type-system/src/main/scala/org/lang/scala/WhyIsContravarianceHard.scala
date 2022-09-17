package org.lang.scala

/**
 * Why is Contravariance hard?
 *
 * @author Sam Ma
 * @date 2022/09/17
 */
object WhyIsContravarianceHard {

  // Known
  val list: List[Int] = List(1, 2, 3)

  class Animal
  class Dog(name: String) extends Animal
  // Question: if Dog <: Animal, does List[Dog] <: List[Animal]? The VARIANCE QUESTION.

  // If yes, then the type is called COVARIANT
  val lassie = new Dog("Lassie")
  val hachi = new Dog("Hachi")
  val laika = new Dog("LaiKa")

  val anAnimal: Animal = lassie // dog
  val myDogs: List[Animal] = List(lassie, hachi, laika) // list of dogs is a list of animals

  // If No, then the type is INVARIANT
  class MyInvariantList[T]
  //  val myDogs2: MyInvariantList[Animal] = new MyInvariantList[Dog] // will not compile
  val myAnimals: MyInvariantList[Animal] = new MyInvariantList[Animal]

  // Hello No, or No, quite the opposite - CONTRAVARIANCE
  class MyContravarianceList[-T]
  val myDogs2: MyContravarianceList[Dog] = new MyContravarianceList[Animal] // ?!

  // a contravariance example，在泛型这块 (-T、+T类型)
  trait Vet[-T] {
    def heal(animal: T): Boolean
  }
  def gimmeAVet(): Vet[Dog] = new Vet[Animal] {
    override def heal(animal: Animal): Boolean = {
      println("you'll be fine")
      true
    }
  }

  val myDog = new Dog("Buddy")
  val anyVet: Vet[Dog] = gimmeAVet()
  anyVet.heal(myDog)  // buddy is happy and healthy

  def main(args: Array[String]): Unit = {

  }

}
