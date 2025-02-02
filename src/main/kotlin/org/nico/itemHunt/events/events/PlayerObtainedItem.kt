package org.nico.itemHunt.events.events

import org.bukkit.event.Event
import org.bukkit.event.HandlerList
import org.bukkit.inventory.ItemStack


class PlayerObtainedItem: Event() {

    private lateinit var newItem: ItemStack

    companion object{
        val HANDLER_LIST: HandlerList = HandlerList()
    }

    override fun getHandlers(): HandlerList {
        return HANDLER_LIST
    }

    override fun callEvent(): Boolean {



        return super.callEvent()
    }

}