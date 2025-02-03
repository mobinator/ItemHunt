package org.nico.itemHunt.inventories.Buttons

import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack
import org.nico.itemHunt.ItemHunt
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.TextColor
import org.bukkit.event.inventory.ClickType


class InventoryButton(
    private val material: Material,
    private var name: String,
    private val inventory: Inventory,
    private val pos: Int,
    val state: () -> ItemStack?,
    val onClick: (clickType: ClickType) -> Unit
): Listener {

    var displayItem: ItemStack = ItemStack.of(material)

    init {
        Bukkit.getPluginManager().registerEvents(this, ItemHunt.instance)

        displayItem.editMeta {
            it.displayName(Component.text(name))
        }

        inventory.setItem(pos, displayItem)
        inventory.setItem(pos + 9, state.invoke())

    }

    @EventHandler
    fun onInventoryClick(event: InventoryClickEvent) {
        if (event.currentItem == displayItem && event.clickedInventory == inventory) {

            event.isCancelled = true

            println("Button clicked ${displayItem.type}")

            onClick(event.click)

            inventory.setItem(pos + 9, state.invoke())

        }
    }
}