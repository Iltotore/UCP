package fr.il_totore.ucp

import fr.il_totore.ucp.parsing.{CommandElement, InputTokenizer, SimpleSplitTokenizer}

class CommandSpec[S](args: Nothing /*TODO*/, executor: CommandExecutor[S], description: Option[String], parser: Nothing /*TODO*/) {

  class Builder {

    var element: CommandElement[S] = _
    var executor: CommandExecutor[S] = _
    var description: String = ""
    var permission: S => Boolean = _ => true
    var tokenizer: InputTokenizer = new SimpleSplitTokenizer(" ")

  }
}
