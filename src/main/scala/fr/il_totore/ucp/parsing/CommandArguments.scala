package fr.il_totore.ucp.parsing

class CommandArguments(raw: String, params: List[SingleParameter]) {

  def getRawInput: String = raw
  def getParameters: List[SingleParameter] = params
}
