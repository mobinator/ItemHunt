package org.nico.itemHunt.utils

import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

fun removeItem(player: Player, targetMaterial: Material, amount: Int = 1) {
    var target = ItemStack.of(targetMaterial)
    var remaining = amount
    val inventory = player.inventory
}
