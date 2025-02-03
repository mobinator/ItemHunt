package org.nico.itemHunt.game

import org.bukkit.Bukkit.broadcast
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.inventory.ItemStack
import org.nico.itemHunt.ItemHunt
import org.nico.itemHunt.events.events.GameStarted
import org.nico.itemHunt.events.events.PlayerObtainedItem
import org.nico.itemHunt.game.items.HuntItem
import org.nico.itemHunt.tasks.ItemTestScheduler
import java.util.logging.Level

class GameEventListener(private val plugin: ItemHunt) : Listener {

    private val logger = plugin.logger
    private var players = emptyList<Player>()
    private var schedules: List<ItemTestScheduler> = emptyList()


    @EventHandler
    fun onGameStarted(event: GameStarted) {
        logger.log(Level.INFO, "Game Started")

        players = plugin.server.onlinePlayers.toList()

        schedules = players.map { player ->
            ItemTestScheduler(
                player = player,
                logger = logger,
                item = ItemStack.of(Material.BEDROCK)
            )
        }

        schedules.forEach {
            plugin.sheduler.runTaskTimer(plugin, it, 0, 20)
        }

//        lookForNewItem()
    }

    @EventHandler
    fun onItemFound(event: PlayerObtainedItem) {
        lookForNewItem()
    }

    private fun lookForNewItem() {

        val newItem = HuntItem.getRandomItem()

        broadcast(newItem.displayName())

        schedules.forEach { schedule ->

            schedule.item = newItem

        }

    }

}