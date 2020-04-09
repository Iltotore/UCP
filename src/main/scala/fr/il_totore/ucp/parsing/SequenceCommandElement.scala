package fr.il_totore.ucp.parsing

import fr.il_totore.ucp.CommandContext
import fr.il_totore.ucp.parsing.ParsingResult._

class SequenceCommandElement[S](key: String, required: Boolean, elements: List[CommandElement[S]]) extends CommandElement[S] {

  override def parse(sender: S, args: CommandArguments, context: CommandContext[S]): ParsingResult[S] = {
    for (element <- this.elements) {
      val result: ParsingResult[S] = element.parse(sender, args, context)
      if (result.getResultType != 0) return result
    }
    SUCCESS parsing args
  }

  override def getKey: String = ???

  override def isRequired: Boolean = ???

  override def getUsage(sender: S): String = ???
}