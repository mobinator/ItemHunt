package org.nico.itemHunt.events

import org.bukkit.Bukkit
import org.bukkit.event.Event
import org.nico.itemHunt.events.events.GameStarted

object EventHandler {

    fun startGame(){
        callEvent(
            GameStarted()
        )
    }


    private fun callEvent(event: Event){
        event.callEvent()
    }
}