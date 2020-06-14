package io.github.il_totore.ucp.registration

import io.github.il_totore.ucp.CommandSpec
import io.github.il_totore.ucp.parsing.ParsingResult

/**
 * A command registry manages registered command and is the parsing process's root.
 *
 * @tparam S the sender type.
 */
trait CommandRegistry[S] {

  /**
   * Parse the given command as String.
   *
   * @param sender  the command sender.
   * @param command the command line as String.
   * @return a ParsingResult[S] containing the context to execute.
   */
  def parse(sender: S, command: String): ParsingResult[S]

  /**
   * Register the given command.
   *
   * @param spec the CommandSpec to register.
   */
  def register(spec: CommandSpec[S]): Unit

  /**
   * Unregister the given command.
   *
   * @param spec the CommandSpec to unregister.
   */
  def unregister(spec: CommandSpec[S]): Unit

  /**
   * Unregister specs where the predicate returns true.
   *
   * @param predicate the tester. Remove the command if it returns true.
   */
  def unregisterIf(predicate: CommandSpec[S] => Boolean): Unit
}