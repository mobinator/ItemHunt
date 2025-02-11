package org.nico.itemHunt.game.items

import org.bukkit.Material
import org.bukkit.inventory.ItemStack
import org.nico.itemHunt.game.data.GameData
import org.nico.itemHunt.teams.ItemHuntTeam
import org.nico.itemHunt.utils.CsvReader
import kotlin.random.Random

object HuntItem {

    var itemPools = CsvReader.generateItemPools().toList()

    //This will only be used for a Fallback
    //generating this takes a lot of time
    //that's why it's lazy
    private val materials by lazy { Material.entries.filter { it.isItem && !it.isLegacy } }

    fun nextItem(team: ItemHuntTeam): ItemStack {

        var item = getRandomItem()

        if (GameData.chainMode) {

            if (GameData.itemQueue.size >= team.score + 1) {
                item = GameData.itemQueue[team.score]
            } else {
                item = getRandomItem()
                GameData.itemQueue.add(item)
            }

        }
        println(GameData.itemQueue)
        return item
    }

    fun getRandomItem(): ItemStack {
        return if (itemPools.isNotEmpty()) {

            val pools = itemPools.filterIndexed { index, _ -> GameData.selectedItemPools[index] > 0 }

            ItemStack.of(weightedRandomItem(pools, GameData.selectedItemPools.filterIndexed { index, _ -> GameData.selectedItemPools[index] > 0 }))
        } else {

            val randomIndex = Random.nextInt(materials.size)
            ItemStack.of(materials[randomIndex])
        }
    }

    fun generateRandomItemList(length: Int): List<ItemStack> {
        return List(length) { getRandomItem() }
    }

    private fun weightedRandomItem(list: List<ItemPool>, weights: List<Int>): Material {
        try {
            val totalWeight = weights.sum()
            var rand = Random.nextInt(totalWeight)
            weights.forEachIndexed { index, weight ->
                if (rand <= weight) {
                    if (index < list.size)
                        return list[index].getRandomItem()
                    else
                        return list.last().getRandomItem()
                }
                rand -= weight
            }
        } catch (e: Exception) {
            println("Error in weightedRandomItem")
        }

        return list.last().getRandomItem()
    }
}
