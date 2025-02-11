package org.nico.itemHunt.inventories.buttons

import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack

class SelectableItem(
    private val selectedItem: ItemStack,
    private val deselectedItem: ItemStack,
    private var isSelected: Boolean,
    inventory: Inventory,
    pos: Int,
    private val onSelect: (selected: Boolean, player: Player) -> Unit,
) {
    init {
        ClickableItem(
            displayItem = {
                if (isSelected) selectedItem else deselectedItem
            },
            inventory = inventory,
            pos = pos,
            onClick = { _, player ->
                if (deselectedItem.type in player.inventory.map { it?.type ?: Material.AIR }) {
                    isSelected = true
                    onSelect(true, player)
                    println("Selected: $isSelected")
                }
            }
        )
    }
}