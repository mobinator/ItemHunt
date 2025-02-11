package org.nico.itemHunt.command.commands

import io.papermc.paper.command.brigadier.BasicCommand
import io.papermc.paper.command.brigadier.CommandSourceStack
import net.kyori.adventure.text.Component
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.nico.itemHunt.ItemHunt
import org.nico.itemHunt.events.ItemHuntEventHandler
import org.nico.itemHunt.game.data.GameData
import org.nico.itemHunt.game.data.GamePhase
import org.nico.itemHunt.teams.ItemHuntTeam

class StartGame : BasicCommand {

    override fun execute(commandSourceStack: CommandSourceStack, args: Array<String>) {

        val name = commandSourceStack.executor?.name ?: commandSourceStack.sender.name

        val playersWithoutTeams = ItemHuntTeam.playersWithoutTeams(ItemHunt.instance.server.onlinePlayers.toList())

        println(ItemHuntTeam.getTeam(commandSourceStack.sender as Player))

        if (!commandSourceStack.sender.isOp) {
            commandSourceStack.sender.sendMessage(Component.text("You do not have permission to use this command"))
            return
        }

        if (GameData.currentGamePhase == GamePhase.PLAYING){
            commandSourceStack.sender.sendMessage(Component.text("Game is already in progress"))
            return
        }

        if (GameData.listMode && GameData.itemsToFind > 54){
            commandSourceStack.sender.sendMessage(Component.text("Too many items to find for list mode please dont go higher than 54 Sorry"))
            return
        }

        if (playersWithoutTeams.isEmpty()) {
            sendMessage(name = name)

            ItemHuntEventHandler.startGame()
        } else {
            commandSourceStack.sender.sendMessage(Component.text("Not enough players to start the game"))
            commandSourceStack.sender.sendMessage(
                Component.text(
                    "Players without teams: ${
                        playersWithoutTeams.map { it.name }.joinToString(", ")
                    }"
                )
            )
        }

    }

    fun sendMessage(name: String) {
        val broadcastMessage = Component.text("$name has started the game")

        Bukkit.broadcast(broadcastMessage)
    }
}