package io.github.iltotore.ucp.spigot

import io.github.iltotore.ucp.{Command, CommandRegistry}
import org.bukkit.plugin.java.JavaPlugin

class SpigotRegistry(plugin: JavaPlugin) extends CommandRegistry.Prefixed[SpigotContext, SpigotResult]("") {

  override def register(command: Command[SpigotContext, SpigotResult]): Unit = {
    super.register(command)
    plugin.getCommand(command.name.name).setExecutor(SpigotExecutor(command.executor, this))
  }

  override def unregister(command: Command[SpigotContext, SpigotResult]): Unit = {
    super.unregister(command)
    plugin.getCommand(command.name.name).setExecutor(null)
  }

}