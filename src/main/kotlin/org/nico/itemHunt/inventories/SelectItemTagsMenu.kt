package org.nico.itemHunt.inventories

import org.bukkit.Bukkit
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.InventoryHolder
import org.bukkit.inventory.ItemStack
import org.nico.itemHunt.game.data.GameData
import org.nico.itemHunt.game.items.HuntItem
import org.nico.itemHunt.inventories.buttons.IncrementInventoryButton

class SelectItemTagsMenu : InventoryHolder {

    private var inventory: Inventory = Bukkit.createInventory(this, 9 * 6)

    init {
        HuntItem.itemPools.forEachIndexed { index, itemPool ->
            val item = itemPool.displayItem
            inventory.setItem(index, ItemStack.of(item))
            IncrementInventoryButton(
                material = item,
                name = itemPool.name,
                state = GameData.selectedItemPools[index],
                defaultState = GameData.defaultItemPools[index],
                description = "Set the weight of this item pool \n 0 = Disabled",
                stateLabel = "Weight",
                inventory = inventory,
                pos = index,
            ) {
                GameData.selectedItemPools[index] = it
            }
        }
    }

    override fun getInventory(): Inventory = inventory
}
