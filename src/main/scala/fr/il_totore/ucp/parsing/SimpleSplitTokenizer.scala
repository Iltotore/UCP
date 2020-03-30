package fr.il_totore.ucp.parsing

import scala.collection.mutable.ListBuffer

class SimpleSplitTokenizer(separator: String) extends InputTokenizer {

  override def tokenize(text: String): List[SingleParameter] = {
    val splitString = text.split(separator)
    val paramList = ListBuffer[SingleParameter]()
    var index = 0
    for (string <- splitString) {
      paramList+=new SingleParameter(string, index)
      index+=string.length
    }
    paramList.toList
  }

}
