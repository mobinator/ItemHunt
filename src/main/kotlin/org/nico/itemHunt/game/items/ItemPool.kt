package org.nico.itemHunt.game.items

import org.bukkit.Material

class ItemPool(
    val name: String,
    var items: List<Material>
) {

    var displayItem: Material

    init {
        items = items.filter { it.isItem && !it.isLegacy }
        displayItem = getRandomItem()
    }

    fun getRandomItem(): Material {
        return items.random()
    }
}