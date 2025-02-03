package org.nico.itemHunt.events.events

import org.bukkit.event.Event
import org.bukkit.event.HandlerList

class GameStarted : Event() {

    companion object {
        @JvmStatic
        private val HANDLER_LIST: HandlerList = HandlerList()

        @JvmStatic
        fun getHandlerList(): HandlerList = HANDLER_LIST
    }

    override fun getHandlers(): HandlerList {
        return HANDLER_LIST
    }

}