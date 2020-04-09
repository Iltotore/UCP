package fr.il_totore.ucp.parsing

class CommandArguments(raw: String, params: List[SingleParameter]) {

  var index: Int = -1;

  def getRawInput: String = raw

  def hasNext: Boolean = index < params.size - 1

  def next: Option[String] = {
    index += 1
    if (!hasNext) return Option.empty
    Option(params(index).getValue)
  }

  def peek: Option[String] = {
    if (!hasNext) return Option.empty
    Option(params(index + 1).getValue)
  }

  def reset(): Unit = index = -1
}