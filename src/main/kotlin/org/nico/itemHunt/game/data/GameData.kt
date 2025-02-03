package org.nico.itemHunt.game.data

class GameData {

    companion object {
        var gameDuration = 30
        var jokers = 3
        var itemsToFind = 5
        var backpacks = false
        var chainMode = false
        var currentGamePhase = GamePhase.LOBBY
    }
}

enum class GamePhase {
    LOBBY,
    PLAYING,

}