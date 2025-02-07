package org.nico.itemHunt.game


import net.kyori.adventure.text.Component
import org.bukkit.Bukkit
import org.bukkit.GameMode
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.HandlerList
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityPickupItemEvent
import org.bukkit.event.entity.FoodLevelChangeEvent
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryDragEvent
import org.bukkit.event.inventory.InventoryMoveItemEvent
import org.bukkit.event.player.PlayerDropItemEvent
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.inventory.ItemStack
import org.nico.itemHunt.ItemHunt
import org.nico.itemHunt.events.events.GameStarted
import org.nico.itemHunt.game.data.GameData
import org.nico.itemHunt.game.data.GamePhase
import org.nico.itemHunt.inventories.SelectItemTagsMenu
import org.nico.itemHunt.inventories.Settings
import org.nico.itemHunt.inventories.TeamSelector
import org.nico.itemHunt.teams.ItemHuntTeam
import org.nico.itemHunt.utils.PlayerUtils

class LobbyManager : Listener {

    private val settingsItem = ItemStack(Material.COMPASS)
    private val teamItem = ItemStack(Material.RED_BED)
    private val itemSelectionItem = ItemStack(Material.CHEST)
    private val itemSelection = SelectItemTagsMenu()

    init {
        settingsItem.editMeta {
            it.displayName(Component.text("Settings"))
        }
        teamItem.editMeta {
            it.displayName(Component.text("Team Selector"))
        }
        itemSelectionItem.editMeta {
            it.displayName(Component.text("Item Selection"))
        }
    }


    @EventHandler
    fun onPlayerJoin(event: PlayerJoinEvent) {
        val player = event.player
        player.gameMode = GameMode.ADVENTURE
        setHotBar(player)
        //Unlock all recipes
        PlayerUtils.unlockAllRecipes(player)
    }

    @EventHandler
    fun onPlayerInteract(event: PlayerInteractEvent) {
        val player = event.player
        val item = event.item ?: return

        if (item.type == settingsItem.type) {

            var settings = Settings()
            player.openInventory(settings.inventory)

        } else if (item.type == teamItem.type) {

            var teamSelector = TeamSelector(player)
            player.openInventory(teamSelector.inventory)
        } else if (item.type == itemSelectionItem.type) {
            player.openInventory(itemSelection.inventory)
        }

        setHotBar(player)

    }

    @EventHandler
    fun onFoodLevelChange(event: FoodLevelChangeEvent) {
        if (event.entity is Player) {
            event.isCancelled = true
            event.entity.foodLevel = 20
            event.entity.saturation = 20f
        }
    }

    @EventHandler
    fun doEntityPickupItemEvent(event: EntityPickupItemEvent) {
        event.isCancelled = true
    }

    @EventHandler
    fun doPlayerDropItemEvent(event: PlayerDropItemEvent) {
        println("dropitem detected $event")
        event.itemDrop.remove()
        event.isCancelled = true
        setHotBar(event.player)
    }

    @EventHandler
    fun doInventoryClickEvent(event: InventoryClickEvent) {
        event.isCancelled = true
    }

    @EventHandler
    fun doInventoryDragEvent(event: InventoryDragEvent) {
        event.isCancelled = true
    }

    @EventHandler
    fun doInventoryMoveItemEvent(event: InventoryMoveItemEvent) {
        event.isCancelled = true
    }

    @EventHandler
    fun onGameStarted(event: GameStarted) {
        HandlerList.unregisterAll(this)
        worldConfig(true)
        GameData.currentGamePhase = GamePhase.PLAYING
        ItemHuntTeam.teams.forEach {
            it.backpack.clear()
        }
    }

    fun setHotBar(player: Player) {
        player.inventory.clear()
        if (player.isOp) {
            player.inventory.setItem(2, settingsItem)
            player.inventory.setItem(4, itemSelectionItem)
            player.inventory.setItem(6, teamItem)
        } else {
            player.inventory.setItem(4, teamItem)
        }
    }

    companion object {
        fun worldConfig(value: Boolean){
            val plugin = ItemHunt.instance

            plugin.server.dispatchCommand(plugin.server.consoleSender, "time set 0")
            plugin.server.dispatchCommand(plugin.server.consoleSender, "weather clear")
            plugin.server.dispatchCommand(plugin.server.consoleSender, "gamerule doDaylightCycle $value")
            plugin.server.dispatchCommand(plugin.server.consoleSender, "gamerule doWeatherCycle $value")
        }
    }

}