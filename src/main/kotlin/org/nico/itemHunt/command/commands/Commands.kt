package org.nico.itemHunt.command.commands

import io.papermc.paper.command.brigadier.Commands

class Commands {
    companion object {
        val startGame = Commands.literal("itemhunt").then(Commands.literal("start")).build()
    }
}