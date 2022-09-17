package org.lang.scala

/**
 * @author Sam Ma
 * @date 2020/06/26
 * 深入学习scala中的for推导式 scala.io.Source读取文件并过滤掉空行
 */
object RemoveBlankApp {

  def main(args: Array[String]): Unit = for {
    filePath <- args
    // 假如文件路径以-字符开始，空白符会被压缩否则就只会除去空白行，src/main/scala/org/lang/scala/RemoveBlank.scala
    (compress, path) = if (filePath startsWith "-") (true, filePath.substring(1))
                       else (false, filePath)
    line <- apply(path, compress)
  } println(line)

  /**
   * 从指定的输入文件中移除空行 使用regex expression进行匹配，
   *
   * @param path
   * @param compressWhiteSpace
   * @return
   */
  def apply(path: String, compressWhiteSpace: Boolean = false): Seq[String] =
    for {
      // getLines()返回scala.collection.Iterator对象，由于for推导式无法返回Iterator对象，故将其转化为一个序列
      line <- scala.io.Source.fromFile(path).getLines().toSeq
      // line.matches使用正则表达式过滤掉文件中的空白行，最终在console中不进行输出
      if line.matches("""^\s*$""") == false
      // 若未开启空白符压缩,那么局部变量将存储未变的非空行 反之则将局部变量设置为一个新的行值，该行值已将所有的空白符压缩为一个空格
      line2 = if (compressWhiteSpace) line replaceAll("\\s+", " ")
              else line
    } yield line2

}


