package io.github.iltotore.ucp

trait CommandExecutor[C <: CommandContext, R] extends (C => R)