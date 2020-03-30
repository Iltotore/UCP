package fr.il_totore.ucp.parsing

import fr.il_totore.ucp.CommandContext

class SequenceCommandElement[S](key: String, required: Boolean, elements: List[CommandElement[S]]) extends CommandElement[S](key, required){

  def parse(source: Nothing, args: Nothing, context: CommandContext): Unit = {
    for (element <- this.elements) {
      element.parse(source, args, context)
    }
  }

  override protected def parseValue(sender: S, arguments: CommandArguments): AnyRef = _
}