package org.nico.itemHunt.events

import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.Event
import org.nico.itemHunt.events.events.GameEnded
import org.nico.itemHunt.events.events.GameStarted
import org.nico.itemHunt.events.events.PlayerObtainedItem

object ItemHuntEventHandler {

    fun startGame() {
        callEvent(
            GameStarted()
        )
    }

    fun stopGame() {
        callEvent(
            GameEnded()
        )
    }

    fun itemFound(item: Material, player:Player) {
        callEvent(
            PlayerObtainedItem(item, player)
        )
    }

    private fun callEvent(event: Event) {
        event.callEvent()
    }
}