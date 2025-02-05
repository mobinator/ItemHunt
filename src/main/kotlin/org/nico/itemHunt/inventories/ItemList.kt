package org.nico.itemHunt.inventories

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import org.bukkit.Bukkit
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.InventoryHolder
import org.bukkit.inventory.ItemStack
import org.nico.itemHunt.inventories.buttons.SelectableItem
import kotlin.math.ceil

class ItemList(val items: List<ItemStack>) : InventoryHolder {

    private val size = ceil(items.size / 9f) * 9

    private var inventory: Inventory = Bukkit.createInventory(
        this,
        size.toInt(),
        Component.text("Settings")
    )

    init {
        items.forEachIndexed { index, item ->
            val selectedItem = item.clone()
            selectedItem.editMeta {
                it.displayName()?.append(Component.text("Found"))?.color(NamedTextColor.GREEN)
            }
            SelectableItem(
                selectedItem = selectedItem,
                deselectedItem = item,
                isSelected = false,
                inventory = this.inventory,
                pos = index,
            ){ selected, player ->
            }
        }
    }

    override fun getInventory(): Inventory = inventory
}
