package fr.il_totore.ucp.registration

import fr.il_totore.ucp.parsing.ParsingResult._
import fr.il_totore.ucp.parsing.{CommandArguments, ParsingResult}
import fr.il_totore.ucp.{CommandContext, CommandSpec}

import scala.collection.mutable
import scala.collection.mutable.ListBuffer

class PrefixedCommandRegistry[S](sequence: ListBuffer[CommandSpec[S]], prefix: String) extends SequenceCommandRegistry[S](sequence) {

  override def parse(sender: S, command: String): ParsingResult[S] = {
    if (!command.startsWith(prefix)) return FAILURE whilst "evaluating prefix"
    val stripedCommand = command.substring(1)
    val spec: Option[CommandSpec[S]] = sequence.find((cmdSpec: CommandSpec[S]) => cmdSpec.getTokenizer.getCommandName(stripedCommand) == cmdSpec.getName)
    if (spec.isEmpty) return FAILURE whilst "matching command"
    val context: CommandContext[S] = new CommandContext(spec.get, mutable.MultiDict.empty)
    val arguments: CommandArguments = new CommandArguments(command, spec.get.getTokenizer.tokenize(stripedCommand))
    spec.get.getElement.parse(sender, arguments, context)
  }
}