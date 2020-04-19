package fr.il_totore.ucp.parsing

import fr.il_totore.ucp.CommandContext
import fr.il_totore.ucp.parsing.CommandElement.NamedElement
import fr.il_totore.ucp.parsing.ParsingResult._

import scala.util.control.Exception.allCatch

abstract class EndElement[S](key: String) extends NamedElement[S](key: String) {

  override def getUsage(sender: S): String = if (isRequired) "<" + key + ">" else "[" + key + "]"
}

object EndElement {

  implicit class LambdaElement[S](key: String) extends EndElement[S](key: String) {

    private var parseFunction: (S, CommandArguments, CommandContext[S]) => ParsingResult[S] = _

    override def parse(sender: S, arguments: CommandArguments, context: CommandContext[S]): ParsingResult[S] = parseFunction.apply(sender, arguments, context)

    def lambda(func: (S, CommandArguments, CommandContext[S]) => ParsingResult[S]): LambdaElement[S] = {
      this.parseFunction = func
      this
    }

    def casting[T](func: String => T): LambdaElement[S] = {

      def castToValue(sender: S, args: CommandArguments, context: CommandContext[S]): ParsingResult[S] = {
        val value: Option[T] = allCatch opt {
          func.apply(args.next.get)
        }
        if (value.isEmpty) return FAILURE parsing args in context
        context.putArgument(key, value.get)
        SUCCESS parsing args in context
      }

      lambda(castToValue)
    }

  }

}
