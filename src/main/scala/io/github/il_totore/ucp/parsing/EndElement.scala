package io.github.il_totore.ucp.parsing

import io.github.il_totore.ucp.CommandContext
import io.github.il_totore.ucp.GeneralResult._
import io.github.il_totore.ucp.parsing.CommandElement.NamedElement
import io.github.il_totore.ucp.parsing.ParsingResult._

import scala.util.control.Exception.allCatch

/**
 * An EndElement is an element at the end of a command fork.
 *
 * @param key the unique key identifying this element.
 * @tparam S the sender type.
 */
abstract class EndElement[S](key: String) extends NamedElement[S](key: String)

object EndElement {

  /**
   * An implicit implementation using a lambda.
   *
   * @param key the unique key identifying this element.
   * @tparam S the sender type.
   * @tparam T the produced object's type.
   */
  implicit class LambdaElement[S, T](key: String) extends EndElement[S](key: String) {

    private var parseFunction: (S, CommandArguments, CommandContext[S]) => ParsingResult[S] = _
    private var default: Option[T] = Option.empty

    override def parse(sender: S, arguments: CommandArguments, context: CommandContext[S]): ParsingResult[S] = parseFunction.apply(sender, arguments, context)

    /**
     * Set behavior function.
     *
     * @param func the new element behavior.
     * @return this LambdaElement for chaining.
     */
    def lambda(func: (S, CommandArguments, CommandContext[S]) => ParsingResult[S]): LambdaElement[S, T] = {
      this.parseFunction = func
      this
    }

    /**
     * A shortcut for converting an object to another.
     *
     * @param func the converting function.
     * @return this LambdaElement for chaining.
     */
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

    /**
     * Set the default option if the parsing function fails.
     *
     * @param default the default Option.
     * @return this LambdaElement for chaining.
     */
    def orElseOption(default: Option[T]): LambdaElement[S, T] = {
      this.default = default
      this
    }

    /**
     * Shortcut for orElseOption(Option(default)).
     *
     * @param default the value to be used as default.
     * @return this LambdaElement for chaining.
     */
    def orElse(default: T): LambdaElement[S, T] = {
      this.default = Option(default)
      this
    }

  }

}
