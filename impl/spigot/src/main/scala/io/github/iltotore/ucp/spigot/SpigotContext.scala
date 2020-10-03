package io.github.iltotore.ucp.spigot

import io.github.iltotore.ucp.CommandContext
import org.bukkit.command.CommandSender

import scala.collection.mutable

class SpigotContext(val sender: CommandSender,
                    map: mutable.Map[String, Any])
        extends CommandContext.Mapped(map)