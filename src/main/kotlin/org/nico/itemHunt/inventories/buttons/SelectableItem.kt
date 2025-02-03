package org.nico.itemHunt.inventories.buttons

import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack

class SelectableItem(
    private val selectedItem: ItemStack,
    private val deselectedItem: ItemStack,
    private var isSelected: Boolean,
    inventory: Inventory,
    pos: Int,
    private val onSelect: (selected: Boolean) -> Unit,
) {
    init {
        ClickableItem(
            displayItem = {
                if (isSelected) selectedItem else deselectedItem
            },
            inventory = inventory,
            pos = pos,
            onClick = {
                isSelected = !isSelected
                onSelect(isSelected)
                println("Selected: $isSelected")
            }
        )
    }
}