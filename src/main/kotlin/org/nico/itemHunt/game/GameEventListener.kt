package org.nico.itemHunt.game

import net.kyori.adventure.text.Component
import net.kyori.adventure.title.Title
import org.bukkit.Bukkit
import org.bukkit.GameMode
import org.bukkit.Location
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.scheduler.BukkitRunnable
import org.nico.itemHunt.ItemHunt
import org.nico.itemHunt.events.events.GameEnded
import org.nico.itemHunt.events.events.GameStarted
import org.nico.itemHunt.events.events.PlayerObtainedItem
import org.nico.itemHunt.game.data.GameData
import org.nico.itemHunt.game.data.GamePhase
import org.nico.itemHunt.teams.ItemHuntTeam
import org.nico.itemHunt.utils.removeItem
import java.time.Duration
import java.util.logging.Level

class GameEventListener(private val plugin: ItemHunt) : Listener {

    private val logger = plugin.logger
    private var players = emptyList<Player>()
    lateinit var gameTimer: GameTimer


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
                removeItem(event.player, event.item, 1)
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

    private fun teleportToSpawn(player: Player) {
        val spawnLocation: Location = Bukkit.getWorlds()[0].spawnLocation
        player.teleport(spawnLocation)
    }

    private fun staggeredPostGameSummary() {
        object : BukkitRunnable() {
            var winner = ItemHuntTeam.teams[0]
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

                    winner = if (team.score > winner.score) team else winner

                    index++
                } else {
                    showWinnerTitle(winner)
                    cancel()
                }
            }
        }.runTaskTimer(plugin, 40L, 20L)
    }

    private fun showWinnerTitle(winner: ItemHuntTeam) {
        object : BukkitRunnable() {
            override fun run() {
                val title = Title.title(
                    Component.text("${winner.teamName} Wins!", winner.teamColor),
                    Component.text("Congratulations!"),
                    Title.Times.times(Duration.ofMillis(500), Duration.ofSeconds(3), Duration.ofMillis(1000))
                )
                players.forEach { player ->
                    player.showTitle(title)
                }
            }
        }.runTaskLater(plugin, 40L)
    }
}