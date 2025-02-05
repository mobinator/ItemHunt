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
        9 * 4,
        Component.text("Settings")
    )

    init {

        generalSettings()

    }

    private fun clearInventory() {
        for (i in 0 until inventory.size) {
            inventory.setItem(i, null)
        }
    }

    private fun generalSettings() {


        clearInventory()

        line1()
        line2()

    }

    private fun line1(){
        //Settings:
        //  - Game duration
        // - Jokers
        // - number of items to find
        // - Backpacks
        // - Chain Mode
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

    private fun line2() {
        // Sleep player percentage
        // Keep inventory
        // Delete Item when found
        // List mode

        IncrementInventoryButton(
            material = Material.RED_BED,
            name = "Sleep Player Percentage",
            state = GameData.sleepPlayerPercentage,
            defaultState = 50,
            description = "The Percentage of Players that need to sleep to skip the night \n L-Click to increase \n R-Click to decrease",
            stateLabel = "Sleep Percentage",
            inventory = inventory,
            pos = 19
        ) {
            GameData.sleepPlayerPercentage = it
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "gamerule playersSleepingPercentage $it")
        }

        ToggleInventoryButton(
            material = Material.CHEST,
            name = "Keep Inventory",
            state = GameData.keepInventory,
            inventory = inventory,
            pos = 21
        ) {
            GameData.keepInventory = it
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "gamerule keepInventory $it")
        }

        ToggleInventoryButton(
            material = Material.BARRIER,
            name = "Delete Item when found",
            state = GameData.deleteItemWhenFound,
            inventory = inventory,
            pos = 23
        ) {
            GameData.deleteItemWhenFound = it
        }

        ToggleInventoryButton(
            material = Material.WRITABLE_BOOK,
            name = "List Mode",
            state = GameData.listMode,
            inventory = inventory,
            pos = 25
        ) {
            GameData.listMode = it
        }

    }

    override fun getInventory(): Inventory = inventory

}