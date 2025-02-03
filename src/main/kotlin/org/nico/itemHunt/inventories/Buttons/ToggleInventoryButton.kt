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
import org.bukkit.inventory.meta.ItemMeta


class ToggleInventoryButton(
    private val material: Material,
    private var name: String,
    private val inventory: Inventory,
    private val pos: Int,
    val onClick: (state: Boolean, item: ItemStack) -> Unit
) {

    private var displayItem = ItemStack.of(material)
    private var enabledItem = ItemStack.of(Material.LIME_STAINED_GLASS_PANE)
    private var disabledItem = ItemStack.of(Material.RED_STAINED_GLASS_PANE)
    var state = disabledItem

    init {

        displayItem.editMeta {
            it.displayName(Component.text(name))
            it.setCustomModelData(pos)
        }

        enabledItem.editMeta {
            it.displayName(Component.text("Enabled", TextColor { 0x00FF00 }))
            it.setCustomModelData(pos)
        }

        disabledItem.editMeta {
            it.displayName(Component.text("Disabled", TextColor { 0xFF0000 }))
            it.setCustomModelData(pos)
        }

        var button = InventoryButton(
            displayItem = { displayItem },
            inventory = inventory,
            pos = pos,
            state = {
                state
            }
        ) {
            handleClick()
        }

    }

    fun handleClick() {
        state = if (state == enabledItem) disabledItem else enabledItem
    }

}