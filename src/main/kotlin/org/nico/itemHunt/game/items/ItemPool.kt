package org.nico.itemHunt.game.items

import org.bukkit.Material

class ItemPool(
    val name: String,
    val items: List<Material>
) {
    fun getRandomItem(): Material {
        return items.random()
    }
}