package org.nico.itemHunt.inventories

import org.bukkit.Bukkit
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.InventoryHolder
import org.nico.itemHunt.game.items.HuntItem

class SelectItemTagsMenu: InventoryHolder {

    private var inventory: Inventory = Bukkit.createInventory(this, 9*6)

    init {

        var items = HuntItem.generateRandomItemList(9*6)

//        val previousPage = ItemStack.of(Material.ARROW)
//        val nextPage = ItemStack.of(Material.DIAMOND_SWORD)
//
//        var previousPageMeta = previousPage.itemMeta
//        previousPageMeta.displayName(Component.text("Previous Page"))
//
//        inventory.setItem(3, nextPage)
//        inventory.setItem(5, previousPage)

        items.forEachIndexed { index, itemStack ->
            inventory.setItem(index, itemStack)
        }
    }

    override fun getInventory(): Inventory = inventory
}