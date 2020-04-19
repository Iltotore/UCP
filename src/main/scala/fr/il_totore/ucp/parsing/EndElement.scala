package fr.il_totore.ucp.parsing

import fr.il_totore.ucp.CommandContext
import fr.il_totore.ucp.parsing.CommandElement.NamedElement
import fr.il_totore.ucp.parsing.ParsingResult._

abstract class EndElement[S](key: String) extends NamedElement[S](key: String) {

  override def getUsage(sender: S): String = if (isRequired) "<" + key + ">" else "[" + key + "]"
}

object EndElement {

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
