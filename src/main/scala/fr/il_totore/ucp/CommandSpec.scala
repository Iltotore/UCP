package fr.il_totore.ucp

import fr.il_totore.ucp.parsing.{CommandElement, InputTokenizer, SimpleSplitTokenizer}

trait CommandSpec[S] {

  def getName: String

  def getElement: CommandElement[S]

  def getExecutor: Option[(S, CommandContext) => CommandResult]

  def getDescription: Option[String]

  def getTokenizer: InputTokenizer
}

object CommandSpec {

  implicit class ImplicitSpec[S](name: String) extends CommandSpec[S] {

    private var element: CommandElement[S] = _ //TODO make null-safe
    private var executor: Option[(S, CommandContext) => CommandResult] = Option.empty
    private var description: Option[String] = Option.empty
    private var permission: S => Boolean = _ => true
    private var tokenizer: InputTokenizer = new SimpleSplitTokenizer(" ")

    def withElement(commandElement: CommandElement[S]): ImplicitSpec[S] = {
      element = commandElement
      this
    }

    def executing(executor: (S, CommandContext) => CommandResult): ImplicitSpec[S] = {
      this.executor = Option(executor)
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

    override def getElement: CommandElement[S] = element

    override def getExecutor: Option[(S, CommandContext) => CommandResult] = executor

    override def getDescription: Option[String] = description

    override def getTokenizer: InputTokenizer = tokenizer
  }

}