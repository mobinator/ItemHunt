package org.nico.itemHunt.teams

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.Listener
import org.bukkit.inventory.ItemStack
import org.nico.itemHunt.ItemHunt
import org.nico.itemHunt.game.items.HuntItem
import org.nico.itemHunt.tasks.ItemTestScheduler

class ItemHuntTeam(
    private val teamName: String,
    private val teamColor: NamedTextColor
): Listener {

    var players: MutableList<Player> = mutableListOf()
    private var schedules: MutableList<ItemTestScheduler> = mutableListOf()
    private var score: Int = 0
    private val plugin = ItemHunt.instance


    init {
        plugin.server.pluginManager.registerEvents(this, plugin)
    }


    fun addPlayer(player: Player) {
        teams.forEach { team ->
            if (team.isMember(player))
                team.removePlayer(player)
        }
        players.add(player)
        player.displayName(
            Component.text(player.name, teamColor)
        )
        addShedule(player)
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

    private fun addShedule(player: Player) {
        val schedule = ItemTestScheduler(
                player = player,
                logger = plugin.logger,
                item = ItemStack.of(Material.BEDROCK)
            )
        schedules.add(
            schedule
        )
        plugin.sheduler.runTaskTimer(plugin, schedule, 0, 20)
    }

    fun itemFound(){
        lookForNewItem()
    }

    private fun lookForNewItem() {
        val newItem = HuntItem.getRandomItem()
        players.forEach { player ->
            player.sendMessage("New item: ${newItem.type}")
        }
        schedules.forEach { schedule ->
            schedule.item = newItem
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

        fun getTeam(player: Player): ItemHuntTeam? {
            teams.forEach { team ->
                if (team.isMember(player))
                    return team
            }
            return null
        }

        fun playersWithoutTeams(players: List<Player>): List<Player> {
            val playersWithoutTeams = mutableListOf<Player>()
            players.forEach { player ->
                if (getTeam(player) == null)
                    playersWithoutTeams.add(player)
            }
            return playersWithoutTeams
        }
    }
}
