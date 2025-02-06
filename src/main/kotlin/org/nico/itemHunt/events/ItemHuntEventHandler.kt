package org.nico.itemHunt.events

import org.bukkit.entity.Player
import org.bukkit.event.Event
import org.bukkit.inventory.ItemStack
import org.nico.itemHunt.events.events.*

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

    fun resetGame() {
        callEvent(
            GameReset()
        )
    }

    fun playerRejoin(player: Player) {
        callEvent(
            PlayerRejoin(player)
        )
    }

    fun itemFound(item: ItemStack, player: Player) {
        callEvent(
            PlayerObtainedItem(item, player)
        )
    }

    private fun callEvent(event: Event) {
        event.callEvent()
    }
}