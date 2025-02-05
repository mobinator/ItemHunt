package org.nico.itemHunt.inventories

import net.kyori.adventure.text.Component
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.InventoryHolder
import org.bukkit.inventory.ItemStack
import org.nico.itemHunt.inventories.buttons.TeamSelectionItem
import org.nico.itemHunt.teams.ItemHuntTeam

class TeamSelector(val player: Player) : InventoryHolder {

    private var inventory: Inventory = Bukkit.createInventory(
        this,
        9 * 1,
        Component.text("Select your Team")
    )

    // Speichert alle SelectableItem-Instanzen, um sie bei Änderungen zu aktualisieren
    private val selectableItems = mutableListOf<TeamSelectionItem>()

    init {
        clearInventory()
        teamSelectorButtons()
    }

    private fun teamSelectorButtons() {
        ItemHuntTeam.teamSelectionItems.forEachIndexed { index, item ->
            val selectedItem = item.clone()
            selectedItem.editMeta {
                it.setEnchantmentGlintOverride(true)
            }
            val team = ItemHuntTeam.teams[index]
            val selectableItem = TeamSelectionItem(
                team = team,
                selectedItem = selectedItem,
                deselectedItem = item,
                isSelected = { team.isMember(player) },
                inventory = inventory,
                pos = index,
                onSelect = { selected ->
                    if (selected) {
                        team.addPlayer(player)
                    } else {
                        team.removePlayer(player)
                    }
                    updateAllSelectableItems()
                }
            )
            selectableItems.add(selectableItem)
        }
        updateAllSelectableItems()
    }

    private fun updateAllSelectableItems() {
        selectableItems.forEach { it.updateItem() }
    }

    private fun clearInventory() {
        for (i in 0 until inventory.size) {
            inventory.setItem(i, null)
        }
    }

    override fun getInventory(): Inventory = inventory
}
