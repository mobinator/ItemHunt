package org.nico.itemHunt.game.items

import org.bukkit.Material
import org.bukkit.inventory.ItemStack
import org.nico.itemHunt.game.data.GameData
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

            val pools = itemPools.filterIndexed { index, _ -> GameData.selectedItemPools[index] > 0 }

            ItemStack.of(weightedRandomItem(pools, GameData.selectedItemPools))
        } else {

            val randomIndex = Random.nextInt(materials.size)
            ItemStack.of(materials[randomIndex])
        }
    }

    fun generateRandomItemList(length: Int): List<ItemStack> {
        return List(length) { getRandomItem() }
    }

    private fun weightedRandomItem(list: List<ItemPool>, weights: List<Int>): Material {
        val totalweight = weights.sum()
        var rand = Random.nextInt(totalweight)
        weights.forEachIndexed() { index, it ->
            rand -= it
            if (rand <= 0) {
                return list[index].getRandomItem()
            }
        }
        return list.random().getRandomItem()
    }
}
