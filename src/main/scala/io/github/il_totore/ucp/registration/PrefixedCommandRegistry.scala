package io.github.il_totore.ucp.registration

import io.github.il_totore.ucp.GeneralResult.{FAILURE, SUCCESS}
import io.github.il_totore.ucp.parsing.ParsingResult._
import io.github.il_totore.ucp.parsing.{CommandArguments, ParsingResult}
import io.github.il_totore.ucp.{CommandContext, CommandSpec}

import scala.collection.mutable

/**
 *
 * @param buffer the Buffer instance used to store registered specs.
 * @param prefix the prefix used before each command. Example: !myCommand
 * @tparam S the sender type.
 */
class PrefixedCommandRegistry[S](buffer: mutable.Buffer[CommandSpec[S]], prefix: String) extends DynamicCommandRegistry[S](buffer) {

  override def parse(sender: S, command: String): ParsingResult[S] = {
    if (!command.startsWith(prefix)) return FAILURE whilst "evaluating prefix"
    val stripedCommand = command.substring(1)
    val spec: Option[CommandSpec[S]] = buffer.find((cmdSpec: CommandSpec[S]) => cmdSpec.getTokenizer.getCommandName(stripedCommand) == cmdSpec.getName)
    if (spec.isEmpty) return FAILURE whilst "matching command"
    val context: CommandContext[S] = new CommandContext(spec.get, mutable.MultiDict.empty)
    val arguments: CommandArguments = new CommandArguments(command, spec.get.getTokenizer.tokenize(stripedCommand))
    if (spec.get.getElement.isDefined) spec.get.getElement.get.parse(sender, arguments, context) else SUCCESS whilst "parsing empty arguments" in context
  }
}