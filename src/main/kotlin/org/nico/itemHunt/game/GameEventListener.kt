package org.nico.itemHunt.game

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import net.kyori.adventure.title.Title
import org.bukkit.Bukkit
import org.bukkit.GameMode
import org.bukkit.Location
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.scheduler.BukkitRunnable
import org.nico.itemHunt.ItemHunt
import org.nico.itemHunt.events.events.*
import org.nico.itemHunt.game.data.GameData
import org.nico.itemHunt.game.data.GamePhase
import org.nico.itemHunt.game.items.HuntItem
import org.nico.itemHunt.inventories.ItemList
import org.nico.itemHunt.teams.ItemHuntTeam
import org.nico.itemHunt.utils.removeItem
import java.time.Duration
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

        GameData.currentGamePhase = GamePhase.PLAYING

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

        staggeredPostGameSummary()
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

    private fun teleportToSpawn(player: Player) {
        val spawnLocation: Location = Bukkit.getWorlds()[0].spawnLocation
        player.teleport(spawnLocation)
    }

    private fun staggeredPostGameSummary() {
        object : BukkitRunnable() {
            var winners = mutableListOf<ItemHuntTeam>()
            val teams = ItemHuntTeam.teams.filter { it.players.isNotEmpty() }.sortedByDescending { it.score }
            var index = -1

            override fun run() {
                if (index == -1) {
                    index = 0
                    return
                }

                if (index < teams.size) {
                    val team = teams[index]
                    logger.log(Level.INFO, "${team.teamName} Score: ${team.score}")
                    Bukkit.broadcast(Component.text("${team.teamName} Score: ${team.score}"))

                    if (team.score != 0) {
                        if (winners.isEmpty())
                            winners.add(team)
                        else if (team.score > winners.first().score)
                            winners = mutableListOf(team)
                        else if (team.score == winners.first().score)
                            winners.add(team)
                    }

                    index++
                } else {
                    showWinnerTitle(winners)
                    cancel()
                }
            }
        }.runTaskTimer(plugin, 40L, 20L)
    }

    private fun showWinnerTitle(winners: List<ItemHuntTeam>) {
        val titleText: String = if (winners.size == 1) {
            "${winners.first().teamName} Wins!"
        } else if (winners.size > 1) {
            "It's a Tie!"
        } else {
            "No Winners"
        }
        val subTitle = if (winners.size == 1) {
            "Congratulations!"
        } else if (winners.size > 1) {
            winners.joinToString(", ") { it.teamName }
        } else {
            "Fucking loosers"
        }
        object : BukkitRunnable() {
            override fun run() {
                val title = Title.title(
                    Component.text(
                        titleText,
                        if (winners.size == 1) winners.first().teamColor else NamedTextColor.WHITE
                    ),
                    Component.text(subTitle),
                    Title.Times.times(Duration.ofMillis(500), Duration.ofSeconds(3), Duration.ofMillis(1000))
                )
                players.forEach { player ->
                    player.showTitle(title)
                }
            }
        }.runTaskLater(plugin, 40L)
    }
}