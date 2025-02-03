package org.nico.itemHunt.command.commands

import io.papermc.paper.command.brigadier.BasicCommand
import io.papermc.paper.command.brigadier.CommandSourceStack
import net.kyori.adventure.text.Component
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.nico.itemHunt.ItemHunt
import org.nico.itemHunt.events.ItemHuntEventHandler
import org.nico.itemHunt.teams.ItemHuntTeam

class StartGame : BasicCommand {

    override fun execute(commandSourceStack: CommandSourceStack, args: Array<String>) {

        val name = commandSourceStack.executor?.name ?: commandSourceStack.sender.name

        val playersWithoutTeams = ItemHuntTeam.playersWithoutTeams(ItemHunt.instance.server.onlinePlayers.toList())

        println(ItemHuntTeam.getTeam(commandSourceStack.sender as Player))

        if (playersWithoutTeams.isEmpty()) {
            sendMessage(name = name)

            ItemHuntEventHandler.startGame()
        } else {
            commandSourceStack.sender.sendMessage(Component.text("Not enough players to start the game"))
            commandSourceStack.sender.sendMessage(Component.text("Players without teams: ${playersWithoutTeams.map { it.displayName() }.joinToString(", ")}"))
        }

    }

    fun sendMessage(name: String) {
        val broadcastMessage = Component.text("$name has started the game")

        Bukkit.broadcast(broadcastMessage)
    }
}