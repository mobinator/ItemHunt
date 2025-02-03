package org.nico.itemHunt.tasks

import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import org.bukkit.scheduler.BukkitTask
import org.nico.itemHunt.events.ItemHuntEventHandler
import java.util.function.Consumer
import java.util.logging.Logger


class ItemTestScheduler(
    private val player: Player,
    private val logger: Logger,
    var item: ItemStack,

    ) : Consumer<BukkitTask?> {

    override fun accept(it: BukkitTask?) {

        if (player.isOnline) {

            if (item in player.inventory) {
                player.sendMessage("You have found the Item. Well done!")
                ItemHuntEventHandler.itemFound(item = item.type, player = player)
            }

            println("Item to find: $item")

        }
    }

}