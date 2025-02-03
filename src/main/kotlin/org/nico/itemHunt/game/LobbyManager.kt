package org.nico.itemHunt.game


import org.bukkit.GameMode
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
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
import org.nico.itemHunt.inventories.Settings
import org.nico.itemHunt.inventories.TeamSelector

class LobbyManager : Listener {


    @EventHandler
    fun onPlayerJoin(event: PlayerJoinEvent) {
        val player = event.player
        player.gameMode = GameMode.ADVENTURE
        setHotBar(player)
    }

    @EventHandler
    fun onPlayerInteract(event: PlayerInteractEvent) {
        val player = event.player
        val item = event.item ?: return

        if (item.type == Material.COMPASS) {

            var settings = Settings()
            player.openInventory(settings.inventory)

        } else if (item.type == Material.RED_BED) {

            var teamSelector = TeamSelector(player)
            player.openInventory(teamSelector.inventory)
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

    fun setHotBar(player: Player) {
        player.inventory.clear()
        player.inventory.setItem(3, ItemStack(Material.COMPASS))
        player.inventory.setItem(5, ItemStack(Material.RED_BED))
    }

}