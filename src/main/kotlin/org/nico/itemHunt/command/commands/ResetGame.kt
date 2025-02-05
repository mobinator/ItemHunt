package org.nico.itemHunt.command.commands

import io.papermc.paper.command.brigadier.BasicCommand
import io.papermc.paper.command.brigadier.CommandSourceStack
import net.kyori.adventure.text.Component
import org.nico.itemHunt.ItemHunt
import org.nico.itemHunt.events.ItemHuntEventHandler
import org.nico.itemHunt.teams.ItemHuntTeam

class ResetGame : BasicCommand {

    override fun execute(commandSourceStack: CommandSourceStack, args: Array<String>) {

        val name = commandSourceStack.executor?.name ?: commandSourceStack.sender.name

        val playersWithoutTeams = ItemHuntTeam.playersWithoutTeams(ItemHunt.instance.server.onlinePlayers.toList())


        ItemHuntEventHandler.resetGame()

        ItemHunt.instance.server.onlinePlayers.forEach { player ->
            if (player.isOp) {
                commandSourceStack.sender.sendMessage(Component.text("Game reset by $name"))
            }
        }


    }

}