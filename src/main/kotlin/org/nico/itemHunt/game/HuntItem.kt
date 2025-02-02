package org.nico.itemHunt.game

import org.bukkit.Material
import org.bukkit.Tag
import org.bukkit.inventory.ItemStack
import kotlin.random.Random

object HuntItem {


    fun getRandomItem(): ItemStack{
        val materials = Material.entries.filter { it.isItem }
        val material = materials[Random.nextInt(materials.size)]
        return ItemStack.of(material)
    }

    fun generateRandomItemList(length: Int): List<ItemStack> {
    return List(length) { getRandomItem() }
    }


}