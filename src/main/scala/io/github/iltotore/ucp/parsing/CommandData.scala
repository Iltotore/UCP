package io.github.iltotore.ucp.parsing

import io.github.iltotore.ucp.{Command, CommandContext}

/**
  * Represent a Command and the passed parameters.
  * @param command the command to be parsed.
  * @param terms the semi-parsed arguments.
  * @tparam C the type of the CommandContext.
  * @tparam R the result type returned by this Command. No type restriction.
  */
case class CommandData[C <: CommandContext, R](command: Command[C, R], terms: TermGroup)