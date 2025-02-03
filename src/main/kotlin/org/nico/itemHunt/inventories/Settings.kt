package org.nico.itemHunt.inventories

import net.kyori.adventure.text.Component
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.InventoryHolder
import org.nico.itemHunt.inventories.buttons.IncrementInventoryButton
import org.nico.itemHunt.inventories.buttons.ToggleInventoryButton

class Settings : InventoryHolder {

    private var inventory: Inventory = Bukkit.createInventory(
        this,
        9 * 2,
        Component.text("Settings")
    )

    init {
        //Settings:
        //  - Game duration
        // - Jokers
        // - number of items to find
        // - Backpacks
        // - Chain Mode

        IncrementInventoryButton(
            material = Material.CLOCK,
            name = "Game Duration",
            description = "The duration of the game \n L-Click to increase \n R-Click to decrease",
            stateLabel = "Duration",
            inventory = inventory,
            pos = 0,
        ) {
            println("Game duration button clicked")
        }

        ToggleInventoryButton(
            material = Material.TOTEM_OF_UNDYING,
            name = "Jokers",
            state = false,
            inventory = inventory,
            pos = 2
        ) {
            println("Jokers button clicked")
        }

        ToggleInventoryButton(
            material = Material.CHEST,
            name = "Items to find",
            state = false,
            inventory = inventory,
            pos = 4
        ) {
            println("Items to find button clicked")
        }

        ToggleInventoryButton(
            material = Material.ENDER_CHEST,
            name = "Backpacks",
            state = false,
            inventory = inventory,
            pos = 6
        ) {
            println("Backpacks button clicked")
        }

        ToggleInventoryButton(
            material = Material.CHAIN,
            name = "Chain Mode",
            state = false,
            inventory = inventory,
            pos = 8
        ) {
            println("Chain mode button clicked")
        }

    }

    override fun getInventory(): Inventory = inventory
}