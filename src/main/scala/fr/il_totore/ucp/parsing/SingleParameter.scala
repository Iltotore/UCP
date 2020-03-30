package fr.il_totore.ucp.parsing

class SingleParameter(value: String, index: Int){

  def getValue: String = value
  def getStartIndex: Int = index
  def getEndIndex: Int = index+value.length
}
