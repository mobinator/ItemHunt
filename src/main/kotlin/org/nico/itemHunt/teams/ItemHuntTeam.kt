package org.nico.itemHunt.teams

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import org.bukkit.entity.Player

class ItemHuntTeam(
    private val teamName: String,
    private val teamColor: NamedTextColor
) {

    private var players: MutableList<Player> = mutableListOf()

    private var score: Int = 0


    fun addPlayer(player: Player) {
        players.add(player)
        teams.forEach { team ->
            if (team.isMember(player))
                team.removePlayer(player)
        }
        player.displayName(Component.text(player.name, teamColor))
    }


    fun removePlayer(player: Player) {
        players.remove(player)
    }


    fun isMember(player: Player): Boolean {
        return players.contains(player)
    }

    fun addScore(points: Int) {
        score += points
    }

    fun resetScore() {
        score = 0
    }


    fun broadcastMessage(message: String) {
        players.forEach { player ->
            player.sendMessage(message)
        }
    }

    companion object {
        var teams: List<ItemHuntTeam> = listOf(
            ItemHuntTeam("White Team", NamedTextColor.WHITE),
            ItemHuntTeam("Blue Team", NamedTextColor.BLUE),
            ItemHuntTeam("Red Team", NamedTextColor.RED),
            ItemHuntTeam("Green Team", NamedTextColor.GREEN),
            ItemHuntTeam("Purple Team", NamedTextColor.DARK_PURPLE),
            ItemHuntTeam("Cyan Team", NamedTextColor.AQUA),
            ItemHuntTeam("Lime Team", NamedTextColor.GREEN),
            ItemHuntTeam("Yellow Team", NamedTextColor.YELLOW),
            ItemHuntTeam("Orange Team", NamedTextColor.GOLD)
        )
    }

}
