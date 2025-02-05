package org.nico.itemHunt.game.items

import org.bukkit.Material
import org.bukkit.inventory.ItemStack
import org.nico.itemHunt.utils.CsvReader
import kotlin.random.Random

object HuntItem {

    var itemPools = CsvReader.generateItemPools().toList()

    //This will only be used for a Fallback
    //generating this takes a lot of time
    //that's why it's lazy
    private val materials by lazy { Material.entries.filter { it.isItem && !it.isLegacy } }

    fun getRandomItem(): ItemStack {
        return if (itemPools.isNotEmpty()) {

            val pools
            val material = itemPools.random().getRandomItem()
            ItemStack.of(material)
        } else {

            val randomIndex = Random.nextInt(materials.size)
            ItemStack.of(materials[randomIndex])
        }
    }

    fun generateRandomItemList(length: Int): List<ItemStack> {
        return List(length) { getRandomItem() }
    }
}
