package org.nico.itemHunt.inventories

import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.InventoryHolder
import org.nico.itemHunt.inventories.Buttons.IncrementInventoryButton
import org.nico.itemHunt.inventories.Buttons.ToggleInventoryButton

class Settings : InventoryHolder {

    private var inventory: Inventory = Bukkit.createInventory(this, 9 * 2)

    init {
        //Settings:
        //  - Game duration
        // - Jokers
        // - number of items to find
        // - Backpacks
        // - Chain Mode

        var gameDurationButton = IncrementInventoryButton(
            material = Material.CLOCK,
            name = "Game Duration",
            description = "The duration of the game \n L-Click to increase \n R-Click to decrease",
            stateLabel = "Duration",
            inventory = inventory,
            pos = 0,
        ) { state, item ->
            println("Game duration button clicked")
        }

        var jokersButton = ToggleInventoryButton(
            material = Material.TOTEM_OF_UNDYING,
            name = "Jokers",
            inventory = inventory,
            pos = 2
        ) { state, item ->
            println("Jokers button clicked")
        }

        var itemsToFindButton = ToggleInventoryButton(
            material = Material.CHEST,
            name = "Items to find",
            inventory = inventory,
            pos = 4
        ) { state, item ->
            println("Items to find button clicked")
        }

        var backpacksButton = ToggleInventoryButton(
            material = Material.ENDER_CHEST,
            name = "Backpacks",
            inventory = inventory,
            pos = 6
        ) { state, item ->
            println("Backpacks button clicked")
        }

        var chainModeButton = ToggleInventoryButton(
            Material.CHAIN,
            "Chain Mode",
            inventory,
            8
        ) { state, item ->
            println("Chain mode button clicked")
        }


    }

    override fun getInventory(): Inventory = inventory
}