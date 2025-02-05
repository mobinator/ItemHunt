package org.nico.itemHunt.inventories.buttons

import org.bukkit.event.inventory.ClickType
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack


class InventoryButton(
    val displayItem: () -> ItemStack,
    private val inventory: Inventory,
    private val pos: Int,
    val state: () -> ItemStack,
    val onClick: (clickType: ClickType) -> Unit
) {


    init {

        ClickableItem(
            displayItem = displayItem,
            inventory = inventory,
            pos = pos,
            onClick = {
                onClick(it)
                inventory.setItem(pos + 9, state.invoke())
            }
        )

        ClickableItem(
            displayItem = state,
            inventory = inventory,
            pos = pos + 9,
            onClick = {
                onClick(it)
                inventory.setItem(pos, displayItem.invoke())
            }
        )
        inventory.setItem(pos + 9, state.invoke())
    }
}