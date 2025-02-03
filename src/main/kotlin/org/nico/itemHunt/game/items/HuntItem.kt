package org.nico.itemHunt.game.items

import org.bukkit.Material
import org.bukkit.inventory.ItemStack
import kotlin.random.Random

object HuntItem {


    private var itemPools: MutableList<ItemPool> = mutableListOf()
    private var poolSize = 0


    fun getRandomItem(): ItemStack {

        if (poolSize == 0) {
            throw IllegalStateException("No item pools added")
        }

        val nextItem = Random.nextInt(poolSize)
        return ItemStack.of(getItemFromPools(nextItem))
    }

    fun generateRandomItemList(length: Int): List<ItemStack> {
        return List(length) { getRandomItem() }
    }

    fun addPool(itemPoolSource: String): HuntItem {
        val pool = ItemPool(itemPoolSource)
        itemPools.add(pool)
        poolSize += pool.items.size
        return this
    }

    private fun getItemFromPools(index: Int): Material {

        var index = index

        itemPools.forEach {
            if (index < it.items.size) {
                return it.items[index]
            } else {
                index -= it.items.size
            }
        }
        return Material.STICK
    }

}