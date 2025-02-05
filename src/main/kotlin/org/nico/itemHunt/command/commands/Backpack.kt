package org.nico.itemHunt.command.commands

import io.papermc.paper.command.brigadier.BasicCommand
import io.papermc.paper.command.brigadier.CommandSourceStack
import org.bukkit.entity.Player
import org.nico.itemHunt.game.data.GameData
import org.nico.itemHunt.game.data.GamePhase
import org.nico.itemHunt.teams.ItemHuntTeam

class Backpack : BasicCommand {

    override fun execute(commandSourceStack: CommandSourceStack, args: Array<String>) {

        if (commandSourceStack.executor is Player) {
            val player = commandSourceStack.executor as Player

            val team = ItemHuntTeam.getTeam(player)

            if (team == null) {
                player.sendMessage("You are not in a team")
                return
            }

            if (!GameData.backpacks) {
                player.sendMessage("Backpacks are disabled")
                return
            }

            if (GameData.currentGamePhase != GamePhase.PLAYING) {
                player.sendMessage("You can only open your backpack during the game")
                return
            }

            player.openInventory(team.backpack)


        } else {
            return
        }
    }
}