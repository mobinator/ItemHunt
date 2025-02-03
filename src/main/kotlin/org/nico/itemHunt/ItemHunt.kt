package org.nico.itemHunt

import net.kyori.adventure.text.Component
import org.bukkit.Bukkit
import org.bukkit.event.EventHandler
import org.bukkit.event.HandlerList
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.plugin.java.JavaPlugin
import org.bukkit.scheduler.BukkitScheduler
import org.nico.itemHunt.events.events.GameStarted
import org.nico.itemHunt.game.GameEventListener
import org.nico.itemHunt.game.LobbyManager
import org.nico.itemHunt.inventories.InventoryEventListener
import org.nico.itemHunt.teams.ItemHuntTeam
import java.util.logging.Level


class ItemHunt : JavaPlugin(), Listener {

    lateinit var sheduler: BukkitScheduler
    private val lobbyManager: LobbyManager = LobbyManager()

    companion object {
        lateinit var instance: ItemHunt
            private set
    }

    override fun onEnable() {
        instance = this

        Bukkit.getPluginManager().registerEvents(this, this)
        Bukkit.getPluginManager().registerEvents(InventoryEventListener(), this)
        Bukkit.getPluginManager().registerEvents(GameEventListener(this), this)
        Bukkit.getPluginManager().registerEvents(lobbyManager, this)

        sheduler = server.scheduler

        logger.log(Level.INFO, "Initilizing Itemhunt Version ${description.version}")
    }

    @EventHandler
    fun onPlayerJoin(event: PlayerJoinEvent) {
        val player = event.player
        player.sendMessage(Component.text("Hello, ${event.player.name}"))
    }

    @EventHandler
    fun onGameStarted(event: GameStarted) {
        HandlerList.unregisterAll(lobbyManager)
    }

    override fun onDisable() {}
}
