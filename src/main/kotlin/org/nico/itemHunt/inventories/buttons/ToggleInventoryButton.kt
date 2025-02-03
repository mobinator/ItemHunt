package org.nico.itemHunt.inventories.buttons

import net.kyori.adventure.text.Component
import org.bukkit.Material
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack


class ToggleInventoryButton(
    material: Material,
    private var name: String,
    var state: Boolean,
    inventory: Inventory,
    private val pos: Int,
    val onClick: (state: Boolean) -> Unit
) {

    private var displayItem = ItemStack.of(material)
    private var enabledItem = ItemStack.of(Material.LIME_STAINED_GLASS_PANE)
    private var disabledItem = ItemStack.of(Material.RED_STAINED_GLASS_PANE)

    init {

        displayItem.editMeta {
            it.displayName(Component.text(name))
            it.setCustomModelData(pos)
        }

        enabledItem.editMeta {
            it.displayName(Component.text("Enabled") { 0x00FF00 })
            it.setCustomModelData(pos)
        }

        disabledItem.editMeta {
            it.displayName(Component.text("Disabled") { 0xFF0000 })
            it.setCustomModelData(pos)
        }

        InventoryButton(
            displayItem = { displayItem },
            inventory = inventory,
            pos = pos,
            state = {
                if (state) enabledItem else disabledItem
            }
        ) {
            state = !state
            onClick(state)
        }

    }


}