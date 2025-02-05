package org.nico.itemHunt.game.data

import org.bukkit.inventory.ItemStack

class GameData {

    companion object {
        var listMode = false
        var deleteItemWhenFound = false
        var keepInventory = false
        var sleepPlayerPercentage = 50
        var gameDuration = 30
        var jokers = 3
        var itemsToFind = 25
        var backpacks = false
        var chainMode = false
        var currentGamePhase = GamePhase.LOBBY
        var defaultItemPools = mutableListOf(10, 10, 0, 0, 0)
        var selectedItemPools = this.defaultItemPools.toMutableList()
        var itemQueue = mutableListOf<ItemStack>()
    }

    fun printAllFields() {
            println("listMode: $listMode")
            println("deleteItemWhenFound: $deleteItemWhenFound")
            println("keepInventory: $keepInventory")
            println("sleepPlayerPercentage: $sleepPlayerPercentage")
            println("gameDuration: $gameDuration")
            println("jokers: $jokers")
            println("itemsToFind: $itemsToFind")
            println("backpacks: $backpacks")
            println("chainMode: $chainMode")
            println("currentGamePhase: $currentGamePhase")
            println("defaultItemPools: $defaultItemPools")
            println("selectedItemPools: $selectedItemPools")
            println("itemQueue: $itemQueue")
        }
}

enum class GamePhase {
    LOBBY,
    PLAYING,
    STOPPED
}