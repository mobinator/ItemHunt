package org.nico.itemHunt.inventories

import net.kyori.adventure.text.Component
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.InventoryHolder
import org.nico.itemHunt.game.data.GameData
import org.nico.itemHunt.inventories.buttons.IncrementInventoryButton
import org.nico.itemHunt.inventories.buttons.ToggleInventoryButton

class Settings : InventoryHolder {

    private var inventory: Inventory = Bukkit.createInventory(
        this,
        9 * 2,
        Component.text("Settings")
    )
    lateinit var currentPage: String

    init {

        generalSettings()

    }

    private fun clearInventory() {
        for (i in 0 until inventory.size) {
            inventory.setItem(i, null)
        }
    }

    private fun generalSettings() {
        //Settings:
        //  - Game duration
        // - Jokers
        // - number of items to find
        // - Backpacks
        // - Chain Mode

        clearInventory()


        IncrementInventoryButton(
            material = Material.CLOCK,
            name = "Game Duration",
            state = GameData.gameDuration,
            defaultState = 30,
            description = "The duration of the game \n L-Click to increase \n R-Click to decrease",
            stateLabel = "Duration",
            inventory = inventory,
            pos = 0,
        ) {
            GameData.gameDuration = it
        }

        IncrementInventoryButton(
            material = Material.TOTEM_OF_UNDYING,
            name = "Jokers",
            state = GameData.jokers,
            defaultState = 3,
            description = "The Number of Jokers for each Player/Team \n L-Click to increase \n R-Click to decrease",
            stateLabel = "Jokers",
            inventory = inventory,
            pos = 2
        ) {
            GameData.jokers = it
        }

        IncrementInventoryButton(
            material = Material.CHEST,
            name = "Number of Items to Find",
            state = GameData.itemsToFind,
            defaultState = 5,
            description = "The Number of Items to find for each Player/Team \n L-Click to increase \n R-Click to decrease",
            stateLabel = "Item count",
            inventory = inventory,
            pos = 4
        ) {
            GameData.itemsToFind = it
        }

        ToggleInventoryButton(
            material = Material.ENDER_CHEST,
            name = "Backpacks",
            state = GameData.backpacks,
            inventory = inventory,
            pos = 6
        ) {
            GameData.backpacks = it
        }

        ToggleInventoryButton(
            material = Material.CHAIN,
            name = "Chain Mode",
            state = GameData.chainMode,
            inventory = inventory,
            pos = 8
        ) {
            GameData.chainMode = it
        }
    }

    override fun getInventory(): Inventory = inventory

}