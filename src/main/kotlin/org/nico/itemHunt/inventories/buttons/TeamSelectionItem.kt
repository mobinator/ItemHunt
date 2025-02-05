package org.nico.itemHunt.inventories.buttons

import net.kyori.adventure.text.Component
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack
import org.nico.itemHunt.teams.ItemHuntTeam

class TeamSelectionItem(
    private val team: ItemHuntTeam,
    private val selectedItem: ItemStack,
    private val deselectedItem: ItemStack,
    private val isSelected: () -> Boolean,
    private val inventory: Inventory,
    private val pos: Int,
    private val onSelect: (selected: Boolean) -> Unit,
) {
    init {
        ClickableItem(
            displayItem = { getDisplayItem() },
            inventory = inventory,
            pos = pos,
            onClick = {
                onSelect(!isSelected.invoke())
                updateItem()
            }
        )
    }

    private fun getDisplayItem(): ItemStack {
        return if (isSelected.invoke()) selectedItem else deselectedItem
    }

    fun updateItem() {
        val displayName = Component.text(team.teamName, team.teamColor)
        val members = team.players.joinToString("\n")
        val lore = team.players.map {
            Component.text(it.name, team.teamColor)
        }

        selectedItem.editMeta { meta ->
            meta.displayName(displayName)
            meta.lore(lore)
            meta.setEnchantmentGlintOverride(true)
        }
        deselectedItem.editMeta { meta ->
            meta.displayName(displayName)
            meta.lore(lore)
        }

        inventory.setItem(pos, getDisplayItem())
    }
}
