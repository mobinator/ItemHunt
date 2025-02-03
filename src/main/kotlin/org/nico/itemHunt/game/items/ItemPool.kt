package org.nico.itemHunt.game.items

import org.bukkit.Material
import org.nico.itemHunt.utils.CsvReader

class ItemPool(private val source: String) {
    val items: MutableList<Material> = mutableListOf()

    init {
        generateItemPoolFromCSV()
    }

    private fun generateItemPoolFromCSV() {
        val lines = CsvReader.readCsvFile(source)
        lines.forEach {
            val material = Material.getMaterial(it)
            if (material != null) {
                items.add(material)
            }
        }
    }

}