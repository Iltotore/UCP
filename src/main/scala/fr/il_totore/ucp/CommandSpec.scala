package fr.il_totore.ucp

import fr.il_totore.ucp.parsing.{CommandElement, InputTokenizer, SimpleSplitTokenizer}

class CommandSpec[S](args: CommandElement[S], executor: CommandExecutor[S], description: Option[String], parser: InputTokenizer) {

  def apply(): Builder[S] = new Builder[S]
}

class Builder[S] {

  var element: CommandElement[S] = _
  var executor: CommandExecutor[S] = _
  var description: Option[String] = Option.empty
  var permission: S => Boolean = _ => true
  var tokenizer: InputTokenizer = new SimpleSplitTokenizer(" ")

  def withElement(commandElement: CommandElement[S]): Builder[S] = {
    element = commandElement
    this
  }

  def execute(executor: CommandExecutor[S]): Builder[S] = {
    this.executor = executor
    this
  }

  def described(description: String): Builder[S] = {
    this.description = Option(description)
    this
  }

  def withPermission(predicate: S => Boolean): Builder[S] = {
    this.permission = predicate
    this
  }

  def tokenized(tokenizer: InputTokenizer): Builder[S] = {
    this.tokenizer = tokenizer
    this
  }
}