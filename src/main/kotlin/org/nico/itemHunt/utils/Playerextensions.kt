package org.nico.itemHunt.utils

import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

/**
 * Entfernt eine bestimmte Anzahl von Items aus dem Inventar des Spielers.
 * Falls der Spieler nicht genügend Items hat, werden so viele wie möglich entfernt.
 *
 * @param target Das ItemStack-Objekt, das entfernt werden soll (Material + Meta, Menge wird ignoriert).
 * @param amount Die Anzahl der zu entfernenden Items (Standard: 1).
 */
fun removeItem(player: Player, targetMaterial: Material, amount: Int = 1) {
    var target = ItemStack.of(targetMaterial)
    var remaining = amount
    val inventory = player.inventory

    for (i in 0 until inventory.size) {
        val item = inventory.getItem(i)

        if (item != null && item.isSimilar(target)) {
            if (item.amount > remaining) {
                item.amount -= remaining
                return // Genügend Items entfernt, fertig
            } else {
                remaining -= item.amount
                inventory.setItem(i, null) // Slot leeren
            }

            if (remaining <= 0) return // Alle Items entfernt
        }
    }
}
