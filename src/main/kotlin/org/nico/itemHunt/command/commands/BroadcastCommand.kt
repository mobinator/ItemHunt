package org.nico.itemHunt.command.commands

import io.papermc.paper.command.brigadier.BasicCommand
import io.papermc.paper.command.brigadier.CommandSourceStack
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.ComponentLike
import net.kyori.adventure.text.minimessage.MiniMessage
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder
import org.bukkit.Bukkit

class BroadcastCommand : BasicCommand {


    override fun execute(commandSourceStack: CommandSourceStack, args: Array<String>) {

        val name = commandSourceStack.executor?.name ?: commandSourceStack.sender.name

        sendMessage(name = name)


    }

    fun sendMessage(name: String){
        val broadcastMessage = Component.text("$name has started the game")

        Bukkit.broadcast(broadcastMessage)
    }

}