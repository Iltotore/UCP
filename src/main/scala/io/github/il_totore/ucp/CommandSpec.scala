package io.github.il_totore.ucp

import io.github.il_totore.ucp.parsing.{CommandElement, InputTokenizer, SimpleSplitTokenizer}

trait CommandSpec[S] {

  def getName: String

  def getElement: Option[CommandElement[S]]

  def getExecutor: (S, CommandContext[S]) => GeneralResult

  def getDescription: Option[String]

  def getTokenizer: InputTokenizer
}

object CommandSpec {

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