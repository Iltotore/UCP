package io.github.iltotore.ucp.example

import java.util.concurrent.TimeUnit

import io.github.iltotore.ucp.{CommandContext, CommandExecutor}

object CommandTimeExecutor extends CommandExecutor[CommandContext.Mapped, String] {

  override def apply(context: CommandContext.Mapped): String = {
    val count: Long = context.get("time").get
    val unit: TimeUnit = context.get("unit").getOrElse(TimeUnit.MILLISECONDS)
    s"Time=$count ${unit.name()}"
  }
}