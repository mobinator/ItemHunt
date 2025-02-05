package org.nico.itemHunt.game.items

import org.bukkit.Material
import org.nico.itemHunt.utils.CsvReader

data class ItemPool(
    val name: String,
    val items: List<Material>
)