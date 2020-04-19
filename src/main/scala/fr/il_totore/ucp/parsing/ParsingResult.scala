package fr.il_totore.ucp.parsing

import fr.il_totore.ucp.CommandContext

trait ParsingResult[S] {

  def getResultType: Int

  def getMessage: String

  def getArguments: Option[CommandArguments]

  def getContext: Option[CommandContext[S]]
}

object ParsingResult {

  val SUCCESS: Int = 0
  val FAILURE: Int = -1

  implicit class ImplicitParsingResult[S](resultType: Int) extends ParsingResult[S] {

    var message: String = ""
    var arguments: Option[CommandArguments] = Option.empty
    var context: Option[CommandContext[S]] = Option.empty

    def whilst(message: String): ImplicitParsingResult[S] = {
      this.message = message
      this
    }

    def forTooManyArguments: ImplicitParsingResult[S] = whilst("Too many arguments")

    def forNotEnoughArguments: ImplicitParsingResult[S] = whilst("Not enough argument")

    def using(arguments: CommandArguments): ImplicitParsingResult[S] = {
      this.arguments = Option(arguments)
      this
    }

    def in(context: CommandContext[S]): ImplicitParsingResult[S] = {
      this.context = Option(context)
      this
    }

    def asParsingResult(): ImplicitParsingResult[S] = this

    def parsing(arguments: CommandArguments): ImplicitParsingResult[S] = whilst("parsing command").using(arguments)

    override def getResultType: Int = resultType

    override def getMessage: String = message

    override def getArguments: Option[CommandArguments] = arguments

    override def getContext: Option[CommandContext[S]] = context
  }

}
