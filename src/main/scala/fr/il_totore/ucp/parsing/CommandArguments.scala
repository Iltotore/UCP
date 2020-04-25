package fr.il_totore.ucp.parsing

class CommandArguments(raw: String, params: List[SingleParameter]) {

  private var index: Int = -1

  def getRawInput: String = raw

  def hasNext: Boolean = index < params.size - 1

  def next: Option[String] = {
    if (!hasNext) return Option.empty
    index += 1
    Option(params(index).getValue)
  }

  def peek: Option[String] = {
    if (!hasNext) return Option.empty
    Option(params(index + 1).getValue)
  }

  def getCurrentIndex: Int = index

  def reset(index: Int): Unit = this.index = index

  def reset(): Unit = reset(-1)

  def back(n: Int): Unit = {
    index -= n
    if (index < -1) index = -1
  }
}