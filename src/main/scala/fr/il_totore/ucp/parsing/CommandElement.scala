package fr.il_totore.ucp.parsing

import fr.il_totore.ucp.CommandContext
import fr.il_totore.ucp.parsing.ParsingResult._

trait CommandElement[S] {

  def getKey: String

  def isRequired: Boolean

  def parse(sender: S, arguments: CommandArguments, context: CommandContext[S]): ParsingResult[S]

  def getUsage(sender: S): String
}

object CommandElement {

  abstract class ImplicitElement[S](key: String) extends CommandElement[S] {

    private var required: Boolean = true

    override def getKey: String = key

    override def isRequired: Boolean = required

    def optional: ImplicitElement[S] = {
      required = false
      this
    }
  }

  abstract class EndElement[S](key: String) extends ImplicitElement[S](key: String) {

    override def getUsage(sender: S): String = if (isRequired) "<" + key + ">" else "[" + key + "]"
  }

  implicit class LambdaElement[S](key: String) extends EndElement[S](key: String) {

    private var parseFunction: (S, CommandArguments, CommandContext[S]) => Any = _

    override def parse(sender: S, arguments: CommandArguments, context: CommandContext[S]): ParsingResult[S] = {
      context.putArgument(key, parseFunction.apply(sender, arguments, context))
      SUCCESS parsing arguments in context
    }

    def lambda(func: (S, CommandArguments, CommandContext[S]) => Any): LambdaElement[S] = {
      this.parseFunction = func
      this
    }
  }


}