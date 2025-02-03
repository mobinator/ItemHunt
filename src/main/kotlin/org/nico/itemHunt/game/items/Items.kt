package org.nico.itemHunt.game.items

import org.bukkit.Material
import org.bukkit.inventory.ItemStack

object Items {

    fun getItems(materials: List<Material>): List<ItemStack> {
        return materials.map { material ->
            ItemStack.of(material)
        }
    }

}