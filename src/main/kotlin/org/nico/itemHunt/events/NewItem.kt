package org.nico.itemHunt.events

import org.bukkit.entity.Player
import org.bukkit.event.Event
import org.bukkit.event.HandlerList
import org.bukkit.inventory.ItemStack
import org.bukkit.scheduler.BukkitScheduler


class NewItem(
    private val players: List<Player>,
    private val sheduler: BukkitScheduler,

    ): Event() {

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