package org.nico.itemHunt.command.commands

import io.papermc.paper.command.brigadier.BasicCommand
import io.papermc.paper.command.brigadier.CommandSourceStack
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import org.nico.itemHunt.inventories.SelectItemTagsMenu

class MenuCommand : BasicCommand {


    override fun execute(commandSourceStack: CommandSourceStack, args: Array<String>) {

        if (commandSourceStack.executor is Player){
            val player = commandSourceStack.executor as Player

            val inventory = SelectItemTagsMenu()

            player.openInventory(inventory.inventory)


        } else {
            return
        }

    }
}