package org.nico.itemHunt.events

import org.bukkit.Material
import org.bukkit.event.Event
import org.nico.itemHunt.events.events.GameStarted
import org.nico.itemHunt.events.events.PlayerObtainedItem

object ItemHuntEventHandler {

    fun startGame(){
        callEvent(
            GameStarted()
        )
    }

    fun itemFound(item: Material){
        callEvent(
            PlayerObtainedItem(item)
        )
    }

    private fun callEvent(event: Event){
        event.callEvent()
    }
}