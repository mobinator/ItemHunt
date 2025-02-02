package org.nico.itemHunt

import net.kyori.adventure.text.Component
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.inventory.ItemStack
import org.bukkit.plugin.java.JavaPlugin
import org.bukkit.scheduler.BukkitScheduler
import org.nico.itemHunt.events.NewItem
import java.util.logging.Level


class ItemHunt : JavaPlugin(), Listener {

    lateinit var sheduler: BukkitScheduler
    var currentItem: ItemStack = ItemStack.of(Material.BEEF)

    override fun onEnable() {
        Bukkit.getPluginManager().registerEvents(this, this)

        sheduler = server.scheduler

        logger.log(Level.INFO, "Initilizing Itemhunt Version ${description.version}")

//        callNewItemEvent()
    }

    override fun onDisable() {
        // Plugin shutdown logic
    }

    @EventHandler
    fun onPlayerJoin(event: PlayerJoinEvent){
        event.player.sendMessage(Component.text("Hello, ${event.player.name}"))
        logger.log(Level.INFO, "Plugin registered join event of ${event.player.name}")



    }

    @EventHandler
    fun onNewItem(event: NewItem){

//        event.
//
//        sheduler.runTaskTimer(
//                this,
//                ItemTestSheduler(
//                    player = player,
//                    logger = logger,
//                    item = currentItem
//                ),
//                0,
//                20
//            )
    }


    fun callNewItemEvent(){
        val event = NewItem(
            players = Bukkit.getOnlinePlayers().toList(),
            sheduler = sheduler        )
        event.callEvent()
    }

}
