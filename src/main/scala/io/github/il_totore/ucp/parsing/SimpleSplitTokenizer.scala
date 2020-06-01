package io.github.il_totore.ucp.parsing

import scala.collection.mutable.ListBuffer

class SimpleSplitTokenizer(separator: String) extends InputTokenizer {

  override def tokenize(text: String): List[SingleParameter] = {
    val splitString = text.split(separator)
    val paramList = ListBuffer[SingleParameter]()
    var index = 0
    if (splitString.size == 1) return paramList.toList
    for (i <- 1 until splitString.length) {
      paramList += new SingleParameter(splitString(i), index)
      index += splitString(i).length
    }
    paramList.toList
  }

  override def getCommandName(text: String): String = text.split(separator)(0)
}
