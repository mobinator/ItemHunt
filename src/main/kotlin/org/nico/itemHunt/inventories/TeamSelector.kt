package org.nico.itemHunt.inventories

import net.kyori.adventure.text.Component
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.InventoryHolder
import org.bukkit.inventory.ItemStack
import org.nico.itemHunt.inventories.buttons.SelectableItem

class TeamSelector(player: Player): InventoryHolder {

    private var inventory: Inventory = Bukkit.createInventory(
        this,
        9 * 1,
        Component.text("Select your Team")
    )

    init {
        clearInventory()
        teamSelectorButtons()
    }

    private fun teamSelectorButtons(){
        listOf(
            ItemStack.of(Material.WHITE_WOOL),
            ItemStack.of(Material.BLUE_WOOL),
            ItemStack.of(Material.RED_WOOL),
            ItemStack.of(Material.GREEN_WOOL),
            ItemStack.of(Material.PURPLE_WOOL),
            ItemStack.of(Material.CYAN_WOOL),
            ItemStack.of(Material.LIME_WOOL),
            ItemStack.of(Material.YELLOW_WOOL),
            ItemStack.of(Material.ORANGE_WOOL),
        ).forEachIndexed() { index, item ->
            val selectedItem = item.clone()
            selectedItem.editMeta {
                it.setEnchantmentGlintOverride(true)
            }
            SelectableItem(
                selectedItem = selectedItem,
                deselectedItem = item,
                isSelected = false,
                inventory = inventory,
                pos = index,
                onSelect = {},
            )
        }
    }

    private fun clearInventory(){
        for (i in 0 until inventory.size){
            inventory.setItem(i, null)
        }
    }

    override fun getInventory(): Inventory = inventory

}
