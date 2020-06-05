package org.lang.scala

/**
 * @author Sam Ma
 * @date 2020/06/05
 * scala中的枚举类型
 */
object BreedEnum extends Enumeration {
  type BreedEnum = Value
  val doberman = Value("Doberman Pinscher")
  val yorkie = Value("Yorkshine Terrier")
  val scottie = Value("Scottish Terrier")
  val dane = Value("Great Dane")
  val portie = Value("Portuguese Water Dog")

  def main(args: Array[String]): Unit = {
    println("id \t breed")
    for (breed <- BreedEnum.values) println(s"${breed.id}\t $breed")

    // 通过自定义filter对BreedEnum.values中的数据进行过滤
    println("just terriers:")
    BreedEnum.values filter (_.toString.endsWith("Terrier")) foreach println

  }

}
