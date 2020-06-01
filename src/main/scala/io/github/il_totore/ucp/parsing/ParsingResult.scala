package io.github.il_totore.ucp.parsing

import io.github.il_totore.ucp.GeneralResult.ResultType
import io.github.il_totore.ucp.{CommandContext, GeneralResult}

trait ParsingResult[S] extends GeneralResult {

  def getArguments: Option[CommandArguments]

  def getContext: Option[CommandContext[S]]

}

object ParsingResult {

  implicit class ImplicitParsingResult[S](resultType: ResultType) extends ParsingResult[S] {

    var message: Option[String] = Option.empty
    var arguments: Option[CommandArguments] = Option.empty
    var context: Option[CommandContext[S]] = Option.empty

    def whilst(message: String): ImplicitParsingResult[S] = {
      this.message = Option(message)
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

    override def getResultType: ResultType = resultType

    override def getMessage: Option[String] = message

    override def getArguments: Option[CommandArguments] = arguments

    override def getContext: Option[CommandContext[S]] = context
  }

}
