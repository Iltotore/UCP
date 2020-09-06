package io.github.iltotore.ucp.parsing

import io.github.iltotore.ucp.{Command, CommandContext}

case class CommandData[C <: CommandContext, R](command: Command[C, R], terms: TermGroup)