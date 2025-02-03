package org.nico.itemHunt

import net.kyori.adventure.text.Component
import org.bukkit.Bukkit
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.plugin.java.JavaPlugin
import org.bukkit.scheduler.BukkitScheduler
import org.nico.itemHunt.game.GameEventListener
import org.nico.itemHunt.inventories.InventoryEventListener
import java.util.logging.Level


class ItemHunt : JavaPlugin(), Listener {

    lateinit var sheduler: BukkitScheduler

    companion object {
        lateinit var instance: ItemHunt
            private set
    }


    override fun onEnable() {
        Bukkit.getPluginManager().registerEvents(this, this)
        Bukkit.getPluginManager().registerEvents(InventoryEventListener(), this)
        Bukkit.getPluginManager().registerEvents(GameEventListener(this), this)

        sheduler = server.scheduler

        logger.log(Level.INFO, "Initilizing Itemhunt Version ${description.version}")

        instance = this
    }

    override fun onDisable() {

    }

    @EventHandler
    fun onPlayerJoin(event: PlayerJoinEvent) {
        event.player.sendMessage(Component.text("Hello, ${event.player.name}"))
        logger.log(Level.INFO, "Plugin registered join event of ${event.player.name}")

    }



}
