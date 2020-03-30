package fr.il_totore.ucp

import fr.il_totore.ucp.parsing.SingleParameter

case class CommandResult(resultType: Int, message: Option[String]) {

  def this(resultType: Int) = this(resultType, Option.empty)
  def this(resultType: Int, message: String) = this(resultType, Option(message))
}