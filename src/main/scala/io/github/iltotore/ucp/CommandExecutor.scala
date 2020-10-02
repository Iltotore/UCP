package io.github.iltotore.ucp

/**
  * A functional interface used for command behaviour.
  * @tparam C the type of the CommandContext.
  * @tparam R the result type returned by this Command. No type restriction.
  */
trait CommandExecutor[C <: CommandContext, R] extends (C => R)