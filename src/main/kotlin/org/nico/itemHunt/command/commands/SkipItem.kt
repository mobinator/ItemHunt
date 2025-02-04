package org.nico.itemHunt.command.commands

import io.papermc.paper.command.brigadier.BasicCommand
import io.papermc.paper.command.brigadier.CommandSourceStack
import net.kyori.adventure.text.Component
import org.bukkit.entity.Player
import org.nico.itemHunt.teams.ItemHuntTeam

class SkipItem : BasicCommand {


    override fun execute(commandSourceStack: CommandSourceStack, args: Array<String>) {

        if (commandSourceStack.sender !is Player) {
            commandSourceStack.sender.sendMessage(Component.text("You must be a player to use this command"))
            return
        }
        val player = commandSourceStack.sender as Player
        val team = ItemHuntTeam.getTeam(player) ?: return
        team.broadcastMessage("${player.name} has skipped the item")
        team.nextItem()

    }
}