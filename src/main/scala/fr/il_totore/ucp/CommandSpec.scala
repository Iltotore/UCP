package fr.il_totore.ucp

import fr.il_totore.ucp.parsing.{CommandElement, InputTokenizer, SimpleSplitTokenizer}

trait CommandSpec[S] {

  def getName: String

  def getElement: Option[CommandElement[S]]

  def getExecutor: (S, CommandContext[S]) => CommandResult

  def getDescription: Option[String]

  def getTokenizer: InputTokenizer
}

object CommandSpec {

  implicit class ImplicitSpec[S](name: String) extends CommandSpec[S] {

    private var element: Option[CommandElement[S]] = Option.empty
    private var executor: (S, CommandContext[S]) => CommandResult = _
    private var description: Option[String] = Option.empty
    private var permission: S => Boolean = _ => true
    private var tokenizer: InputTokenizer = new SimpleSplitTokenizer(" ")

    def withElement(commandElement: CommandElement[S]): ImplicitSpec[S] = {
      element = Option(commandElement)
      this
    }

    def executing(executor: (S, CommandContext[S]) => CommandResult): ImplicitSpec[S] = {
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

    override def getExecutor: (S, CommandContext[S]) => CommandResult = executor

    override def getDescription: Option[String] = description

    override def getTokenizer: InputTokenizer = tokenizer
  }

}