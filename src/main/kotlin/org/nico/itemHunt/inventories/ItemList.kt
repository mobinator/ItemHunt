package org.nico.itemHunt.inventories

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import org.bukkit.Bukkit
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.InventoryHolder
import org.bukkit.inventory.ItemStack
import org.nico.itemHunt.inventories.buttons.SelectableItem
import kotlin.math.ceil

class ItemList(var items: MutableList<ItemStack>) : InventoryHolder {

    private val size = ceil(items.size / 9f) * 9
    private var inventory: Inventory = Bukkit.createInventory(
        this,
        size.toInt(),
        Component.text("Items to Find")
    )

    var selectedItems = MutableList(items.size) { false }

    init {
        items.forEachIndexed { index, item ->
            val selectedItem = item.clone()
            selectedItem.editMeta { meta ->
                val currentName = meta.displayName() ?: Component.text(item.type.name)
                meta.displayName(Component.text("Found: ").append(currentName).color(NamedTextColor.GREEN))
                meta.setEnchantmentGlintOverride(true)
            }
            SelectableItem(
                selectedItem = selectedItem,
                deselectedItem = item,
                isSelected = selectedItems[index],
                inventory = this.inventory,
                pos = index
            ) { selected, player ->
                selectedItems[index] = selected
                inventory.setItem(index, if (selected) selectedItem else item)
                println("Item at slot $index selected: $selected")
                player.inventory.removeItem(item)
            }
            inventory.setItem(index, item)
        }
    }

    override fun getInventory(): Inventory = inventory
}
