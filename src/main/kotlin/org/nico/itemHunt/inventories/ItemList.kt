package org.nico.itemHunt.inventories

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.Sound
import org.bukkit.entity.Player
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.InventoryHolder
import org.bukkit.inventory.ItemStack
import org.nico.itemHunt.game.data.GameData
import org.nico.itemHunt.inventories.buttons.SelectableItem
import org.nico.itemHunt.teams.ItemHuntTeam
import org.nico.itemHunt.utils.removeItem
import kotlin.math.ceil

class ItemList(items: List<ItemStack>) : InventoryHolder {

    private val size = ceil(items.size / 9f) * 9
    private var inventory: Inventory = Bukkit.createInventory(
        this,
        size.toInt(),
        Component.text("Items to Find")
    )

    private var selectedItems = MutableList(items.size) { false }

    init {
        inventory.forEachIndexed { index, _ ->
            val item = if(index < items.size) items[index] else ItemStack(Material.BEDROCK)
            val selectedItem = item.clone()
            selectedItem.editMeta { meta ->
                val currentName = meta.displayName() ?: Component.text(item.type.name)
                meta.displayName(Component.text("Found: ").append(currentName).color(NamedTextColor.GREEN))
                meta.setEnchantmentGlintOverride(true)
            }
            SelectableItem(
                selectedItem = selectedItem,
                deselectedItem = item,
                isSelected = selectedItems[index],
                inventory = this.inventory,
                pos = index
            ) { selected, player ->
                selectedItems[index] = selected
                inventory.setItem(index, if (selected) selectedItem else item)

                itemFound(player, ItemHuntTeam.getTeam(player), item)
                if (GameData.deleteItemWhenFound)
                    removeItem(player, item.type)
                ItemHuntTeam.getTeam(player)?.addScore(1)
                ItemHuntTeam.getTeam(player)?.broadcastMessage("${player.name} found ${item.type.name}")
            }
            inventory.setItem(index, item)
        }
    }

    private fun itemFound(player: Player, team: ItemHuntTeam?, item: ItemStack){
        if (team == null){
            return
        }

        if (GameData.deleteItemWhenFound)
                removeItem(player, item.type)
        team.addScore(1)
        team.broadcastMessage("${player.name} found ${item.type.name}")
        team.playSound(Sound.BLOCK_NOTE_BLOCK_PLING, 1f, 2f)
    }

    override fun getInventory(): Inventory = inventory
}
