package io.github.iltotore.ucp.spigot

import io.github.iltotore.ucp.CommandExecutor
import io.github.iltotore.ucp.parsing.Tokenizer
import io.github.iltotore.ucp.util.Fun
import org.bukkit.command.{Command, CommandSender, CommandExecutor => BukkitExecutor}

import scala.collection.mutable


case class SpigotExecutor(child: CommandExecutor[SpigotContext, SpigotResult], registry: SpigotRegistry) extends BukkitExecutor {
  override def onCommand(sender: CommandSender, command: Command, label: String, args: Array[String]): Boolean = {
    val context = new SpigotContext(sender, mutable.Map.empty)
    registry.parseString(context, args.mkString(" "))(SpigotExecutor.tokenizer)
      .recover(throw _)
      .map(Fun.sideEffect(_.message.foreach(sender.spigot().sendMessage)))
      .map(Fun.sideEffect(_.translatedMessages.foreach(sender.spigot().sendMessage)))
      .map(_.valid)
      .get
  }
}

object SpigotExecutor {

  val tokenizer: Tokenizer = new Tokenizer.Regex(" ")
}
