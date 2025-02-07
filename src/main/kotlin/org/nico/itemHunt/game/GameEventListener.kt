package org.nico.itemHunt.game

import org.bukkit.Bukkit
import org.bukkit.GameMode
import org.bukkit.Location
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerAdvancementDoneEvent
import org.nico.itemHunt.ItemHunt
import org.nico.itemHunt.events.events.*
import org.nico.itemHunt.game.data.GameData
import org.nico.itemHunt.game.data.GamePhase
import org.nico.itemHunt.game.items.HuntItem
import org.nico.itemHunt.game.postgame.PostGameSummary
import org.nico.itemHunt.inventories.ItemList
import org.nico.itemHunt.teams.ItemHuntTeam
import org.nico.itemHunt.utils.removeItem
import java.util.logging.Level

class GameEventListener(private val plugin: ItemHunt) : Listener {

    private val logger = plugin.logger
    private var players = emptyList<Player>()
    private lateinit var gameTimer: GameTimer


    @EventHandler
    fun onGameStarted(event: GameStarted) {
        players = plugin.server.onlinePlayers.toList()


        logger.log(Level.INFO, "Game Started")

        players.forEach { player ->
            player.inventory.clear()
            player.gameMode = GameMode.SURVIVAL
        }

        plugin.server.dispatchCommand(plugin.server.consoleSender, "advancement revoke @a everything")

        val chainedItemList = if (GameData.chainMode) HuntItem.generateRandomItemList(GameData.itemsToFind) else null

        ItemHuntTeam.teams.forEach { team ->
            if (team.players.isNotEmpty()) {
                team.resetScore()
                if (GameData.listMode) {
                    if (GameData.chainMode && chainedItemList != null) {
                        team.itemList = ItemList(chainedItemList)
                    } else {
                        team.itemList = ItemList(HuntItem.generateRandomItemList(GameData.itemsToFind))
                    }
                } else {
                    team.itemList = null
                    team.nextItem()
                }
            }
        }

        gameTimer = GameTimer(plugin)
        gameTimer.start()
    }

    @EventHandler
    fun onItemFound(event: PlayerObtainedItem) {
        ItemHuntTeam.teams.forEach { team ->
            if (team.isMember(event.player)) {
                team.addScore(1)
                team.itemFound(event.player)
                if (GameData.deleteItemWhenFound)
                    removeItem(event.player, event.item.type)
            }
        }
    }

    @EventHandler
    fun onGameEnded(event: GameEnded) {
        logger.log(Level.INFO, "Game Ended")
        players.forEach { player ->
            player.gameMode = GameMode.ADVENTURE
            teleportToSpawn(player)
        }
        GameData.currentGamePhase = GamePhase.STOPPED

        PostGameSummary(players, plugin).staggeredPostGameSummary()
    }

    @EventHandler
    fun onGameReset(event: GameReset) {
        val lobbyManager = LobbyManager()
        players.forEach { player ->
            player.gameMode = GameMode.ADVENTURE
            lobbyManager.setHotBar(player)
        }
        GameData.currentGamePhase = GamePhase.LOBBY
        ItemHuntTeam.teams.forEach { team ->
            team.reset()
        }
        Bukkit.getPluginManager().registerEvents(lobbyManager, plugin)
        gameTimer.reset()


    }

    @EventHandler
    fun onPlayerRejoin(event: PlayerRejoin) {
        val player = event.player
        ItemHuntTeam.teams.forEach { team ->
            if (team.isMember(player))
                team.reAddPlayer(player)
        }
    }

    @EventHandler
    fun onAdvancementDone(event: PlayerAdvancementDoneEvent) {
        val message = event.message()
        if (message != null) {
            ItemHuntTeam.getTeam(event.player)?.broadcastMessage(message)
        }
    }

    private fun teleportToSpawn(player: Player) {
        val spawnLocation: Location = Bukkit.getWorlds()[0].spawnLocation
        player.teleport(spawnLocation)
    }

}