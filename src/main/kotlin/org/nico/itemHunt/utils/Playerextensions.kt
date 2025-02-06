package org.nico.itemHunt.utils

import org.bukkit.Material
import org.bukkit.entity.Player

fun removeItem(player: Player, targetMaterial: Material, amount: Int = 1) {
    var remaining = amount
    val inventory = player.inventory

    for (slot in 0 until inventory.size) {
        if (remaining <= 0) break

        val item = inventory.getItem(slot)
        if (item != null && item.type == targetMaterial) {
            if (item.amount <= remaining) {
                remaining -= item.amount
                inventory.setItem(slot, null)
            } else {
                item.amount -= remaining
                remaining = 0
            }
        }
    }
}

