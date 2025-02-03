package org.nico.itemHunt.command.commands

import io.papermc.paper.command.brigadier.BasicCommand
import io.papermc.paper.command.brigadier.CommandSourceStack
import net.kyori.adventure.text.Component
import org.bukkit.Bukkit
import org.nico.itemHunt.events.ItemHuntEventHandler

class StopGame : BasicCommand {

    override fun execute(commandSourceStack: CommandSourceStack, args: Array<String>) {

        val name = commandSourceStack.executor?.name ?: commandSourceStack.sender.name

        sendMessage(name = name)

        ItemHuntEventHandler.stopGame()

    }

    fun sendMessage(name: String) {
        val broadcastMessage = Component.text("$name has ended the game")

        Bukkit.broadcast(broadcastMessage)
    }
}