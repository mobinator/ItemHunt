package org.nico.itemHunt.command.commands

import io.papermc.paper.command.brigadier.BasicCommand
import io.papermc.paper.command.brigadier.CommandSourceStack
import org.bukkit.entity.Player
import org.nico.itemHunt.inventories.Settings

class SettingsCommand : BasicCommand {


    override fun execute(commandSourceStack: CommandSourceStack, args: Array<String>) {

        if (commandSourceStack.executor is Player) {
            val player = commandSourceStack.executor as Player

            val inventory = Settings()

            player.openInventory(inventory.inventory)


        } else {
            return
        }

    }
}