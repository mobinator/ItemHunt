package org.nico.itemHunt.tasks

import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import org.bukkit.scheduler.BukkitTask
import org.nico.itemHunt.events.ItemHuntEventHandler
import java.util.function.Consumer
import java.util.logging.Logger

class ItemTestScheduler(
    val player: Player,
    private val logger: Logger,
    var item: ItemStack,
) : Consumer<BukkitTask?> {

    private var task: BukkitTask? = null

    override fun accept(bukkitTask: BukkitTask?) {
        if (task == null) {
            task = bukkitTask
        }

        if (player.isOnline) {
            if (item.type in player.inventory.map { it?.type ?: Material.AIR }) {
                player.sendMessage("You have found the Item. Well done!")
                ItemHuntEventHandler.itemFound(item = item, player = player)
                cancel()
            }
        }
    }

    fun cancel() {
        logger.info("Cancelling task for player ${player.name}")
        task?.cancel()
        task = null
    }
}
