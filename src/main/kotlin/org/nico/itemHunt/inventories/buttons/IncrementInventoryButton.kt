package org.nico.itemHunt.inventories.buttons

import net.kyori.adventure.text.Component
import org.bukkit.Material
import org.bukkit.event.inventory.ClickType
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack

class IncrementInventoryButton(
    material: Material,
    private var name: String,
    private var state: Int,
    private val defaultState: Int,
    private val description: String,
    private val stateLabel: String,
    inventory: Inventory,
    private val pos: Int,
    val onClick: (state: Int) -> Unit
) {

    private var displayItem = ItemStack.of(material)


    init {

        InventoryButton(
            displayItem = {
                displayItem.editMeta {
                    it.displayName(Component.text(name))
                    it.lore(
                        "$description \n Current State: $state".split("\n").map { line -> Component.text(line.trim()) })
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
                    it.lore(
                        "Normal Click +/- 1 \n Shift Click +/- 10 \n Middle-Click to reset".split("\n")
                            .map { line -> Component.text(line.trim()) })
                    it.setCustomModelData(pos)
                }
                stateItem
            },
            onClick = {
                when (it) {
                    ClickType.LEFT -> {
                        state++
                    }

                    ClickType.RIGHT -> {
                        state--
                    }

                    ClickType.SHIFT_LEFT -> {
                        state += 10
                    }

                    ClickType.SHIFT_RIGHT -> {
                        state -= 10
                    }

                    ClickType.MIDDLE -> {
                        state = defaultState
                    }

                    else -> {
                    }
                }
                if (state < 0) {
                    state = 0
                }
                onClick(state)
            }
        )
    }
}