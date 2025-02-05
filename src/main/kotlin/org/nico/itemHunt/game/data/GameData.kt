package org.nico.itemHunt.game.data

class GameData {

    companion object {
        var listMode = false
        var deleteItemWhenFound = false
        var keepInventory = false
        var sleepPlayerPercentage = 50
        var gameDuration = 30
        var jokers = 3
        var itemsToFind = 5
        var backpacks = false
        var chainMode = false
        var currentGamePhase = GamePhase.LOBBY
        var defaultItemPools = mutableListOf(10, 10, 0, 0, 0)
        var selectedItemPools = this.defaultItemPools.toMutableList()
    }
}

enum class GamePhase {
    LOBBY,
    PLAYING,
    STOPPED
}