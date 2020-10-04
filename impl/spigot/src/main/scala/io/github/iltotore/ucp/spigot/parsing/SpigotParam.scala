package io.github.iltotore.ucp.spigot.parsing

import io.github.iltotore.ucp.parsing.GenericParam
import org.bukkit.entity.Player
import org.bukkit.{Bukkit, OfflinePlayer, World}

object SpigotParam {

  def player(key: String): GenericParam[Player] = GenericParam(key)(Bukkit.getPlayer)

  @SuppressWarnings(Array("deprecation"))
  def offlinePlayer(key: String): GenericParam[OfflinePlayer] = GenericParam(key)(Bukkit.getOfflinePlayer)

  def world(key: String): GenericParam[World] = GenericParam(key)(Bukkit.getWorld)
}
