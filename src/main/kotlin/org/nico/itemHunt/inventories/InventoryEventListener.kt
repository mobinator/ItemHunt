package org.nico.itemHunt.inventories

import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent


class InventoryEventListener : Listener {

    @EventHandler
    fun onInventoryClick(event: InventoryClickEvent) {

        if (event.clickedInventory == null || event.clickedInventory?.getHolder(false) !is SelectItemTagsMenu) {
            return
        } else {
            val inventory = event.clickedInventory?.getHolder(false) as SelectItemTagsMenu

            event.isCancelled = true

            val item = event.currentItem ?: return

            println(item.displayName())


        }


    }

}