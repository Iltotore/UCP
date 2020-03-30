package fr.il_totore.ucp.parsing

trait InputTokenizer {
  def tokenize(text: String): List[SingleParameter]
}
