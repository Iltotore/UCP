package fr.il_totore.ucp.registration

import fr.il_totore.ucp.CommandSpec
import fr.il_totore.ucp.parsing.ParsingResult

trait CommandRegistry[S] {

  def parse(sender: S, command: String): ParsingResult[S]

  def register(spec: CommandSpec[S]): Unit

  def unregister(spec: CommandSpec[S]): Unit

  def filter(predicate: CommandSpec[S] => Boolean): Unit
}