package org.nico.itemHunt.inventories.Buttons

import org.bukkit.Material
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack
import net.kyori.adventure.text.Component
import org.bukkit.event.inventory.ClickType

class IncrementInventoryButton(
    private val material: Material,
    private var name: String,
    private val description: String,
    private val stateLabel: String,
    private val inventory: Inventory,
    private val pos: Int,
    val onClick: (state: Int, item: ItemStack) -> Unit
) {

    var state = 30
    private var displayItem = ItemStack.of(material)


    init {

        InventoryButton(
            displayItem = {
                displayItem.editMeta {
                    it.displayName(Component.text(name))
                    it.lore("$description \n Current State: $state".split("\n").map { line -> Component.text(line.trim()) })
                    it.setCustomModelData(pos)
                }
                displayItem
            },
            inventory = inventory,
            pos = pos,
            state = {
                val stateItem = ItemStack.of(Material.PAPER)
                stateItem.editMeta {
                    it.displayName(Component.text("$stateLabel: $state"))
                    it.lore("Normal Click +/- 1 \n Shift Click +/- 10 \n Middle-Click to reset".split("\n").map { line -> Component.text(line.trim()) })
                    it.setCustomModelData(pos)
                }
                stateItem
            },
            onClick = {
                if (it == ClickType.LEFT) {
                    state++
                } else if (it == ClickType.RIGHT) {
                    state--
                } else if (it == ClickType.SHIFT_LEFT) {
                    state += 10
                } else if (it == ClickType.SHIFT_RIGHT) {
                    state -= 10
                } else if (it == ClickType.MIDDLE) {
                    state = 30
                }
            }
        )
    }
}