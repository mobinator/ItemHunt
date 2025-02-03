package org.nico.itemHunt.game.items

import org.bukkit.Material
import org.bukkit.Tag

class ItemTags {

    companion object {

        val BASIC_BLOCKS = listOf(
            Tag.LOGS,
//            Tag.ITEMS_VILLAGER_PLANTABLE_SEEDS,
            Tag.CROPS,
            Material.COBBLESTONE,
        )

        val ADVANCED_BLOCKS = listOf(
            Tag.COAL_ORES,
            Tag.GOLD_ORES,
            Tag.IRON_ORES,
            Tag.LAPIS_ORES,
            Tag.COPPER_ORES,
            Tag.DIAMOND_ORES,
        )

        val BASIC_ITEMS = listOf(
            Material.BONE,
            Material.GLOWSTONE_DUST,
            Material.REDSTONE,
            Material.RED_DYE
        )

        val DIFFICULT_ITEMS = listOf(
            Tag.ITEMS_CREEPER_DROP_MUSIC_DISCS
        )

        val BASIC_CRAFTABLE = listOf(
            Tag.BEDS,
            Tag.ITEMS_BOATS,
            Tag.ITEMS_CHEST_BOATS,
            Tag.BUTTONS,
            Tag.STONE_BUTTONS,
            Tag.DOORS,
            Material.BOOKSHELF,
            Material.BOOK,
            Material.CAMPFIRE,
            Material.CHEST,
            Material.TRAPPED_CHEST,
            Material.COMPASS,
        )

        val ADVANCED_CRAFTABLE = listOf(
            Tag.CANDLES,
            Material.SOUL_CAMPFIRE,
            Material.ENDER_CHEST,
        )

        val BASIC_TOOLS = listOf(
            Tag.ITEMS_HEAD_ARMOR,
            Tag.ITEMS_CHEST_ARMOR,
            Tag.ITEMS_LEG_ARMOR,
            Tag.ITEMS_FOOT_ARMOR,
            Tag.ITEMS_AXES,
            Tag.ITEMS_PICKAXES,
            Tag.ITEMS_SHOVELS,
            Tag.ITEMS_HOES,
            Tag.ITEMS_SWORDS,
        )

        val ADVANCED_TOOLS = listOf(
            Tag.ITEMS_ARROWS,
        )

        val FIND_IN_OVERWOLRD = listOf(
            Tag.ITEMS_AXOLOTL_FOOD
        )

        val BANNERS = listOf(
            Tag.BANNERS
        )

//        val BLANK = listOf()

    }

}