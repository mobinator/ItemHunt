package org.nico.itemHunt.command.commands

import io.papermc.paper.command.brigadier.BasicCommand
import io.papermc.paper.command.brigadier.CommandSourceStack
import net.kyori.adventure.text.Component
import org.bukkit.entity.Player
import org.nico.itemHunt.inventories.Settings

class SettingsCommand : BasicCommand {


    override fun execute(commandSourceStack: CommandSourceStack, args: Array<String>) {

        if (commandSourceStack.executor is Player) {
            val player = commandSourceStack.executor as Player

            if (!commandSourceStack.sender.isOp) {
                commandSourceStack.sender.sendMessage(Component.text("You do not have permission to use this command"))
                return
            }


            val inventory = Settings()

            player.openInventory(inventory.inventory)


        } else {
            return
        }

    }
}