package org.nico.itemHunt.inventories.buttons

import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.ClickType
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack
import org.nico.itemHunt.ItemHunt

class ClickableItem(
    val displayItem: () -> ItemStack,
    private val inventory: Inventory,
    private val pos: Int,
    val onClick: (clickType: ClickType, player: Player) -> Unit
) : Listener {

    init {
        Bukkit.getPluginManager().registerEvents(this, ItemHunt.instance)

        inventory.setItem(pos, displayItem.invoke())
    }

    @EventHandler
    fun onInventoryClick(event: InventoryClickEvent) {
        if (event.clickedInventory == inventory && event.currentItem == displayItem.invoke()) {

            event.isCancelled = true

            onClick(event.click, event.whoClicked as Player)

            inventory.setItem(pos, displayItem.invoke())
        }
    }
}