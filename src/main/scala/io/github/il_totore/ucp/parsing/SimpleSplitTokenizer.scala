package io.github.il_totore.ucp.parsing

import scala.collection.mutable.ArrayBuffer

/**
 * Tokenize the given command line by using String#split.
 *
 * @param separator the split separator.
 */
class SimpleSplitTokenizer(separator: String) extends InputTokenizer {

  override def tokenize(text: String): Vector[SingleParameter] = {
    val splitString = text.split(separator)
    val paramList = ArrayBuffer[SingleParameter]()
    var index = 0
    if (splitString.size == 1) return paramList.toVector
    for (i <- 1 until splitString.length) {
      paramList += new SingleParameter(splitString(i), index)
      index += splitString(i).length
    }
    paramList.toVector
  }

  override def getCommandName(text: String): String = text.split(separator)(0)
}
