package org.nico.itemHunt.game

import org.bukkit.GameMode
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.nico.itemHunt.ItemHunt
import org.nico.itemHunt.events.events.GameStarted
import org.nico.itemHunt.events.events.PlayerObtainedItem
import org.nico.itemHunt.game.data.GameData
import org.nico.itemHunt.game.data.GamePhase
import org.nico.itemHunt.teams.ItemHuntTeam
import java.util.logging.Level

class GameEventListener(private val plugin: ItemHunt) : Listener {

    private val logger = plugin.logger
    private var players = emptyList<Player>()


    @EventHandler
    fun onGameStarted(event: GameStarted) {
        players = plugin.server.onlinePlayers.toList()


        logger.log(Level.INFO, "Game Started")

        players.forEach { player ->
            player.inventory.clear()
            player.gameMode = GameMode.SURVIVAL
        }

        ItemHuntTeam.teams.forEach { team ->
            if (team.players.isNotEmpty()) {
                team.resetScore()
                team.nextItem()
            }
        }
    }

    @EventHandler
    fun onItemFound(event: PlayerObtainedItem) {
        ItemHuntTeam.teams.forEach { team ->
            if (team.isMember(event.player)) {
                team.addScore(1)
                team.itemFound(event.player)
            }
        }
    }
}