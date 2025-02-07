package org.nico.itemHunt.game.postgame

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import net.kyori.adventure.title.Title
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.scheduler.BukkitRunnable
import org.nico.itemHunt.ItemHunt
import org.nico.itemHunt.teams.ItemHuntTeam
import java.time.Duration

class PostGameSummary(val players: List<Player>, val plugin: ItemHunt) {

    fun staggeredPostGameSummary() {
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