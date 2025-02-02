package org.nico.itemHunt

import net.kyori.adventure.text.Component
import org.bukkit.Bukkit
import org.bukkit.Bukkit.broadcast
import org.bukkit.Material
import org.bukkit.Tag
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.inventory.ItemStack
import org.bukkit.plugin.java.JavaPlugin
import org.bukkit.scheduler.BukkitScheduler
import org.nico.itemHunt.events.events.GameStarted
import org.nico.itemHunt.events.events.PlayerObtainedItem
import org.nico.itemHunt.game.HuntItem
import org.nico.itemHunt.inventories.InventoryEventListener
import org.nico.itemHunt.tasks.ItemTestScheduler
import java.io.File
import java.util.logging.Level


class ItemHunt : JavaPlugin(), Listener {

    lateinit var sheduler: BukkitScheduler
    var players: List<Player> = emptyList()
    private var shedules: List<ItemTestScheduler> = emptyList()

    var huntItems: List<Tag<Material>> = emptyList()

    override fun onEnable() {
        Bukkit.getPluginManager().registerEvents(this, this)
        Bukkit.getPluginManager().registerEvents(InventoryEventListener(), this)

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

        players = server.onlinePlayers.toList()

        shedules = players.map { player ->
            ItemTestScheduler(
                player = player,
                logger = logger,
                item = ItemStack.of(Material.BEDROCK)
            )
        }

        shedules.forEach {
            sheduler.runTaskTimer(this, it, 0, 20)
        }

        lookForNewItem()
    }

    @EventHandler
    fun onItemFound(event: PlayerObtainedItem){
        lookForNewItem()
    }

    private fun lookForNewItem(){

        val newItem = HuntItem.getRandomItem()

        broadcast(newItem.displayName())

        shedules.forEach { shedule ->

            shedule.item = newItem

        }

    }

}
