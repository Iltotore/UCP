package io.github.il_totore.ucp.parsing

import io.github.il_totore.ucp.CommandContext
import io.github.il_totore.ucp.GeneralResult._
import io.github.il_totore.ucp.parsing.CommandElement.NamedElement
import io.github.il_totore.ucp.parsing.ParsingResult._

import scala.util.control.Exception.allCatch

abstract class EndElement[S](key: String) extends NamedElement[S](key: String)

object EndElement {

  implicit class LambdaElement[S, T](key: String) extends EndElement[S](key: String) {

    private var parseFunction: (S, CommandArguments, CommandContext[S]) => ParsingResult[S] = _
    private var default: Option[T] = Option.empty

    override def parse(sender: S, arguments: CommandArguments, context: CommandContext[S]): ParsingResult[S] = parseFunction.apply(sender, arguments, context)

    def lambda(func: (S, CommandArguments, CommandContext[S]) => ParsingResult[S]): LambdaElement[S, T] = {
      this.parseFunction = func
      this
    }

    def casting(func: String => T): LambdaElement[S, T] = {

      def castToValue(sender: S, args: CommandArguments, context: CommandContext[S]): ParsingResult[S] = {
        val value: Option[T] = allCatch opt {
          func.apply(args.next.get)
        }
        if (value.isEmpty && default.isEmpty) return FAILURE parsing args in context
        context.putArgument(key, value.orElse(default).get)
        SUCCESS parsing args in context
      }

      lambda(castToValue)
    }

    def orElseOption(default: Option[T]): LambdaElement[S, T] = {
      this.default = default
      this
    }

    def orElse(default: T): LambdaElement[S, T] = {
      this.default = Option(default)
      this
    }

  }

}
