package io.github.il_totore.ucp.parsing

trait InputTokenizer {
  def getCommandName(text: String): String

  def tokenize(text: String): List[SingleParameter]
}
