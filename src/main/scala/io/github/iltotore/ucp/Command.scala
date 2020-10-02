package io.github.iltotore.ucp

import io.github.iltotore.ucp.parsing.param.{MiscParam, Param}


/**
* An executable command.
* @param name the command name and aliases to identify this command.
* @param executor the executed CommandExecutor when this command is issued.
* @param root the parent parameter of this command, MiscParam.Empty by default.
* @tparam C the type of the CommandContext.
* @tparam R the result type returned by this Command. No type restriction.
*/
case class Command[C <: CommandContext, R](name: CommandName, executor: CommandExecutor[C, R], root: Param = MiscParam.empty)