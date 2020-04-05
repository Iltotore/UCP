package fr.il_totore.ucp.parsing

import fr.il_totore.ucp.CommandContext

class SequenceCommandElement[S](key: String, required: Boolean, elements: List[CommandElement[S]]) extends CommandElement[S](key, required){

  override def parse(sender: S, args: CommandArguments, context: CommandContext): Unit = {
    for (element <- this.elements) {
      element.parse(sender, args, context)
    }
  }

  override def canParse(sender: S, args: CommandArguments): Boolean = {
    elements.exists(element => !element.canParse(sender, args))
  }

  override def parseValue(sender: S, arguments: CommandArguments): AnyRef = null
}