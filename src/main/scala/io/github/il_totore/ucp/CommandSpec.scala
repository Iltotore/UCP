package io.github.il_totore.ucp

import io.github.il_totore.ucp.parsing.{CommandElement, InputTokenizer, SimpleSplitTokenizer}

/**
 * A CommandSpec contains all required specifications to create a command.
 *
 * @tparam S The sender generic type
 */
trait CommandSpec[S] {

  /**
   * Get the command's name
   *
   * @return the command's name, used as identifier.
   */
  def getName: String

  /**
   * Get the command element.
   *
   * @return an optional containing the command element only if this command has parameters.
   */
  def getElement: Option[CommandElement[S]]

  /**
   * Get the command executor
   *
   * @return the element's behavior represented by a (S, CommandContext[S]) => GeneralResult
   */
  def getExecutor: (S, CommandContext[S]) => GeneralResult

  /**
   * Get the command description
   *
   * @return the command description as optional. Empty by default.
   */
  def getDescription: Option[String]

  /**
   * Get the command tokenizer.
   *
   * @return the command tokenizer.
   */
  def getTokenizer: InputTokenizer
}

object CommandSpec {

  /**
   * An implicit implementation of CommandSpec.
   * Example:
   * <pre>
   * "myCommand" describedAs "a test" executing something requiring myElement.
   * <pre>
   *
   * @param name the name instantiating implicitly this class.
   * @tparam S The sender generic type.
   */
  implicit class ImplicitSpec[S](name: String) extends CommandSpec[S] {

    private var element: Option[CommandElement[S]] = Option.empty
    private var executor: (S, CommandContext[S]) => GeneralResult = _
    private var description: Option[String] = Option.empty
    private var permission: S => Boolean = _ => true
    private var tokenizer: InputTokenizer = new SimpleSplitTokenizer(" ")

    def requiring(commandElement: CommandElement[S]): ImplicitSpec[S] = {
      element = Option(commandElement)
      this
    }

    def executing(executor: (S, CommandContext[S]) => GeneralResult): ImplicitSpec[S] = {
      this.executor = executor
      this
    }

    def describedAs(description: String): ImplicitSpec[S] = {
      this.description = Option(description)
      this
    }

    def withPermission(predicate: S => Boolean): ImplicitSpec[S] = {
      this.permission = predicate
      this
    }

    def tokenized(tokenizer: InputTokenizer): ImplicitSpec[S] = {
      this.tokenizer = tokenizer
      this
    }

    override def getName: String = name

    override def getElement: Option[CommandElement[S]] = element

    override def getExecutor: (S, CommandContext[S]) => GeneralResult = executor

    override def getDescription: Option[String] = description

    override def getTokenizer: InputTokenizer = tokenizer
  }

}