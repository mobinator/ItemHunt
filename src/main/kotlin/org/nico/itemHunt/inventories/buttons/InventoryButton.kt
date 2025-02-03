package org.nico.itemHunt.inventories.buttons

import org.bukkit.Bukkit
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.ClickType
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack
import org.nico.itemHunt.ItemHunt


class InventoryButton(
    val displayItem: () -> ItemStack,
    private val inventory: Inventory,
    private val pos: Int,
    val state: () -> ItemStack,
    val onClick: (clickType: ClickType) -> Unit
) : Listener {


    init {
        Bukkit.getPluginManager().registerEvents(this, ItemHunt.instance)

        inventory.setItem(pos, displayItem.invoke())
        inventory.setItem(pos + 9, state.invoke())

    }

    @EventHandler
    fun onInventoryClick(event: InventoryClickEvent) {
        if ((event.currentItem in listOf(
                displayItem.invoke(),
                state.invoke()
            )) && event.clickedInventory == inventory
        ) {

            event.isCancelled = true

            onClick(event.click)

            inventory.setItem(pos, displayItem.invoke())
            inventory.setItem(pos + 9, state.invoke())
        }
    }
}