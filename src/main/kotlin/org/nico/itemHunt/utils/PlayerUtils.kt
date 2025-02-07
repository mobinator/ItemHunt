package org.nico.itemHunt.utils

import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.nico.itemHunt.ItemHunt

object PlayerUtils {
    fun unlockAllRecipes(player: Player) {

        ItemHunt.instance.server.dispatchCommand(Bukkit.getConsoleSender(), "recipe give ${player.name} *")
    }
}