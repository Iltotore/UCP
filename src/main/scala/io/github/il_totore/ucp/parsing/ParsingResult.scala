package io.github.il_totore.ucp.parsing

import io.github.il_totore.ucp.GeneralResult.ResultType
import io.github.il_totore.ucp.{CommandContext, GeneralResult}

/**
 * The result returned by a command parsing process.
 *
 * @tparam S the sender type.
 */
trait ParsingResult[S] extends GeneralResult {

  def getArguments: Option[CommandArguments]

  def getContext: Option[CommandContext[S]]

}

object ParsingResult {

  /**
   * An implicit implementation of ParsingResult
   *
   * @param resultType
   * @tparam S the sender type.
   */
  implicit class ImplicitParsingResult[S](resultType: ResultType) extends ParsingResult[S] {

    var message: Option[String] = Option.empty
    var arguments: Option[CommandArguments] = Option.empty
    var context: Option[CommandContext[S]] = Option.empty

    /**
     * Set the given String as result message.
     *
     * @param message the future result message.
     * @return this ImplicitParsingResult for chaining.
     */
    def whilst(message: String): ImplicitParsingResult[S] = {
      this.message = Option(message)
      this
    }

    /**
     * Shortcut for whilst("Too many arguments")
     *
     * @return this ImplicitParsingResult for chaining.
     */
    def forTooManyArguments: ImplicitParsingResult[S] = whilst("Too many arguments")

    /**
     * Shortcut for whilst("Not enough argument")
     *
     * @return this ImplicitParsingResult for chaining.
     */
    def forNotEnoughArguments: ImplicitParsingResult[S] = whilst("Not enough argument")

    /**
     * Set the given CommandArguments as processed arguments.
     *
     * @param arguments the processed CommandArguments.
     * @return this ImplicitParsingResult for chaining.
     */
    def using(arguments: CommandArguments): ImplicitParsingResult[S] = {
      this.arguments = Option(arguments)
      this
    }

    /**
     * Set the given CommandContext as processed context.
     *
     * @param context the processed context.
     * @return this ImplicitParsingResult for chaining.
     */
    def in(context: CommandContext[S]): ImplicitParsingResult[S] = {
      this.context = Option(context)
      this
    }

    /**
     * Mark explicitly this result as ParsingResult to avoid implicit conflicts.
     *
     * @return this ImplicitParsingResult for chaining.
     */
    def asParsingResult(): ImplicitParsingResult[S] = this

    /**
     * Shortcut for whilst("parsing command").using(arguments)
     *
     * @param arguments the processed arguments.
     * @return this ParsingResult for chaining.
     */
    def parsing(arguments: CommandArguments): ImplicitParsingResult[S] = whilst("parsing command").using(arguments)

    override def getResultType: ResultType = resultType

    override def getMessage: Option[String] = message

    override def getArguments: Option[CommandArguments] = arguments

    override def getContext: Option[CommandContext[S]] = context
  }

}
