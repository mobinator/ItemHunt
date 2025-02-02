package org.nico.itemHunt

import net.kyori.adventure.text.Component
import org.bukkit.Bukkit
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.plugin.java.JavaPlugin
import org.bukkit.scheduler.BukkitScheduler
import org.nico.itemHunt.events.events.GameStarted
import java.util.logging.Level


class ItemHunt : JavaPlugin(), Listener {

    lateinit var sheduler: BukkitScheduler

    override fun onEnable() {
        Bukkit.getPluginManager().registerEvents(this, this)

        sheduler = server.scheduler

        logger.log(Level.INFO, "Initilizing Itemhunt Version ${description.version}")

    }

    override fun onDisable() {
        // Plugin shutdown logic
    }

    @EventHandler
    fun onPlayerJoin(event: PlayerJoinEvent) {
        event.player.sendMessage(Component.text("Hello, ${event.player.name}"))
        logger.log(Level.INFO, "Plugin registered join event of ${event.player.name}")

    }

    @EventHandler
    fun onGameStarted(event: GameStarted) {
        logger.log(Level.INFO,"Game Started")
    }

}
