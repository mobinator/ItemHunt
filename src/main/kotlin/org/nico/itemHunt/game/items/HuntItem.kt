package org.nico.itemHunt.game.items

import org.bukkit.Material
import org.bukkit.inventory.ItemStack
import org.nico.itemHunt.utils.CsvReader
import kotlin.random.Random

object HuntItem {

    private var itemPools = CsvReader.generateItemPools().toList()
    private var poolSize = 0
    private val materials = Material.entries.filter { it.isItem && !it.isLegacy }

    fun getRandomItem(): ItemStack {
        return if (itemPools.isNotEmpty()) {


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

    private fun getItemFromPools(index: Int): Material {
        var indexRemaining = index
        itemPools.forEach { pool ->
            if (indexRemaining < pool.items.size) {
                return pool.items[indexRemaining]
            } else {
                indexRemaining -= pool.items.size
            }
        }

        return Material.STICK
    }
}
