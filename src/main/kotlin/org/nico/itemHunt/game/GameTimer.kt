package org.nico.itemHunt.game

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import org.bukkit.Bukkit
import org.bukkit.boss.BarColor
import org.bukkit.boss.BarStyle
import org.bukkit.boss.BossBar
import org.bukkit.scheduler.BukkitRunnable
import org.nico.itemHunt.ItemHunt
import org.nico.itemHunt.events.ItemHuntEventHandler
import org.nico.itemHunt.game.data.GameData
import org.nico.itemHunt.game.data.GamePhase

class GameTimer(private val plugin: ItemHunt) {

    private var timeLeft = GameData.gameDuration * 60
    private var bossBar: BossBar = Bukkit.createBossBar(
        "Time Left: $timeLeft",
        BarColor.RED,
        BarStyle.SOLID
    )

    private var task: BukkitRunnable? = null

    fun start() {
        timeLeft = GameData.gameDuration * 60
        updateBossBar()
        addAllPlayersToBossBar()

        task = object : BukkitRunnable() {
            override fun run() {
                if (timeLeft <= 0) {
                    stop()
                    return
                }
                timeLeft--
                updateBossBar()
            }
        }
        try {
            (task as BukkitRunnable).runTaskTimer(plugin, 0L, 20L)
        } catch (e: IllegalStateException) {
            e.printStackTrace()
        }
    }

    fun stop() {
        task?.cancel()
        GameData.currentGamePhase = GamePhase.LOBBY
        bossBar.removeAll()
        Bukkit.broadcast(Component.text("Game Over!", NamedTextColor.GOLD))
        ItemHuntEventHandler.stopGame()
    }

    private fun updateBossBar() {
        val hours = timeLeft / 3600
        val minutes = timeLeft % 3600 / 60
        val seconds = timeLeft % 60
        val timeFormatted = String.format("%02d:%02d:%02d", hours, minutes, seconds)
        bossBar.setTitle("Time Left: $timeFormatted")
        bossBar.progress = timeLeft.toDouble() / GameData.gameDuration.toDouble() / 60.0
    }

    private fun addAllPlayersToBossBar() {
        Bukkit.getOnlinePlayers().forEach { bossBar.addPlayer(it) }
    }
}
