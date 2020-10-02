package io.github.iltotore

package object ucp {

  type CommandExecutor[C <: CommandContext, R] = C => R
}