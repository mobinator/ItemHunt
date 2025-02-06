package org.nico.itemHunt.command.commands

import io.papermc.paper.command.brigadier.BasicCommand
import io.papermc.paper.command.brigadier.CommandSourceStack
import net.kyori.adventure.text.Component
import org.bukkit.entity.Player
import org.jetbrains.annotations.ApiStatus
import org.nico.itemHunt.game.data.GameData
import org.nico.itemHunt.teams.ItemHuntTeam

class SkipItem : BasicCommand {


    override fun execute(commandSourceStack: CommandSourceStack, args: Array<String>) {

        if (commandSourceStack.sender !is Player) {
            commandSourceStack.sender.sendMessage(Component.text("You must be a player to use this command"))
            return
        }
        if (GameData.listMode) {
            commandSourceStack.sender.sendMessage(Component.text("You can't skip items in list mode"))
            return
        }
        val player = commandSourceStack.sender as Player
        val team = ItemHuntTeam.getTeam(player) ?: return
        team.broadcastMessage("${player.name} has skipped the item")
        team.nextItem()

    }
}