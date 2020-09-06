package io.github.iltotore.ucp

import io.github.iltotore.ucp.parsing.{MiscParam, Param}

case class Command[C <: CommandContext, R](name: CommandName, executor: CommandExecutor[C, R], root: Param = MiscParam.Empty)