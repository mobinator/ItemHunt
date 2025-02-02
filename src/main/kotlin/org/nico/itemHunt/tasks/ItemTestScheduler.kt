package org.nico.itemHunt.tasks

import java.util.logging.Level
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import org.bukkit.scheduler.BukkitTask
import java.util.function.Consumer
import java.util.logging.Logger


class ItemTestScheduler(
    private val player: Player,
    private val logger: Logger,
    private val item: ItemStack,

    ) : Consumer<BukkitTask?> {

    override fun accept(it: BukkitTask?) {

        if (!player.isOnline){
            it?.cancel()
        }

        if (item in player.inventory){
            logger.log(Level.INFO, "Item found: ${item.displayName()}")
        }

    }

}