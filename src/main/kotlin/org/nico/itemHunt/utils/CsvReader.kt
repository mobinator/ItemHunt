package org.nico.itemHunt.utils

import org.bukkit.Material
import org.nico.itemHunt.game.items.ItemPool

object CsvReader {

    fun readCsvFile(path: String): List<ItemCategory> {
        val file = java.io.File(path)
        return file.readLines().mapNotNull { line ->

            val parts = line.split(",")

            if (parts.size >= 2) {

                val item = parts[0].trim()
                val category = parts[1].trim().toIntOrNull()

                if (category != null) {
                    ItemCategory(item, category)
                } else {
                    null
                }
            } else {
                null
            }
        }
    }

    fun generateItemPools() = sequence<ItemPool> {
        val items = readCsvFile("itempools/cathegorized_items.csv")
        val categoryNames = listOf(
            "Easy",
            "Slight Inconvenience",
            "Medium",
            "Hard",
            "End"
        )
        repeat(5){
            val category = it + 1
            val categorizedItems = items.filter {item -> item.category == category }.mapNotNull { item -> Material.matchMaterial(item.items) }

            val categoryName = categoryNames[it]
            yield(ItemPool(categoryName, categorizedItems))
        }

    }
}

data class ItemCategory(
    val items: String,
    val category: Int
)
