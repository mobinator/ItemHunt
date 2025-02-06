package org.nico.itemHunt.teams

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.Listener
import org.bukkit.inventory.ItemStack
import org.bukkit.scoreboard.*
import org.nico.itemHunt.ItemHunt
import org.nico.itemHunt.game.items.HuntItem
import org.nico.itemHunt.inventories.ItemList
import org.nico.itemHunt.tasks.ItemTestScheduler

class ItemHuntTeam(
    val teamName: String,
    val teamColor: NamedTextColor
) : Listener {

    var players: MutableList<Player> = mutableListOf()
    private var schedules: MutableList<ItemTestScheduler> = mutableListOf()
    var score: Int = 0
    private val plugin = ItemHunt.instance
    private var scoreboard: Scoreboard = Bukkit.getScoreboardManager().mainScoreboard
    private var scoreboardTeam: Team? = null
    private var scoreObjective: Objective? = null
    val backpack = Bukkit.createInventory(null, 9 * 6, Component.text("Backpack"))
    var itemList: ItemList? = null
    var currentItem: ItemStack? = null


    init {
        plugin.server.pluginManager.registerEvents(this, plugin)

        scoreboardTeam = scoreboard.getTeam(teamName) ?: scoreboard.registerNewTeam(teamName).apply {
            color(teamColor)
            setOption(Team.Option.NAME_TAG_VISIBILITY, Team.OptionStatus.ALWAYS)
            setOption(Team.Option.COLLISION_RULE, Team.OptionStatus.ALWAYS)
        }

        scoreObjective = scoreboard.getObjective("teamScore") ?: scoreboard.registerNewObjective(
            "teamScore",
            Criteria.DUMMY,
            Component.text("Score")
        ).apply { displaySlot = DisplaySlot.BELOW_NAME }
    }

    fun addPlayer(player: Player) {
        teams.forEach { team ->
            if (team.isMember(player))
                team.removePlayer(player)
        }
        players.add(player)
        addShedule(player)

        player.displayName(Component.text(player.name, teamColor))
        player.playerListName(Component.text(player.name, teamColor))

        scoreboardTeam?.addEntry(player.name)
    }

    fun removePlayer(player: Player) {
        players.remove(player)
        scoreboardTeam?.removeEntry(player.name)

        player.displayName(Component.text(player.name, NamedTextColor.WHITE))
        player.playerListName(Component.text(player.name, NamedTextColor.WHITE))

        scoreboard.getObjective("teamScore")?.getScore(player.name)?.score = 0
    }

    fun reAddPlayer(player: Player) {
        players.add(player)
        schedules.forEach {
            if (it.player.name == player.name) {
                it.cancel()
            }
        }
        addShedule(player)

        player.displayName(Component.text(player.name, teamColor))
        player.playerListName(Component.text(player.name, teamColor))

        scoreboardTeam?.addEntry(player.name)
    }

    fun addScore(points: Int) {
        score += points
        updateAllPlayerScores()
    }

    fun resetScore() {
        score = 0
        updateAllPlayerScores()
        currentItem = null
    }

    private fun updatePlayerScore(player: Player) {
        scoreObjective?.getScore(player.name)?.score = score
    }

    private fun updateAllPlayerScores() {
        players.forEach { updatePlayerScore(it) }
    }

    fun isMember(player: Player): Boolean {
        return players.map { it.name }.contains(player.name)
    }

    fun broadcastMessage(message: String, playerToExclude: Player? = null) {
        players.forEach { player ->
            if (player != playerToExclude)
                player.sendMessage(message)
        }
    }

    private fun addShedule(player: Player) {
        val schedule = ItemTestScheduler(
            player = player,
            logger = plugin.logger,
            item = currentItem ?: ItemStack.of(Material.BEDROCK)
        )
        schedules.add(schedule)
        plugin.sheduler.runTaskTimer(plugin, schedule, 0, 20)
    }

    fun itemFound(player: Player? = null) {
        player?.sendMessage("${player.name} has found the item")
        nextItem()
        updateDisplayName()
        println(score)
    }

    fun updateDisplayName() {
        players.forEachIndexed() { index, player ->
            players[index].displayName(
                Component.text(player.name, teamColor)
            )
        }
    }

    fun nextItem() {
        val newItem = HuntItem.nextItem(this)
        currentItem = newItem.clone()

        broadcastMessage("New item: ${newItem.type}")

        schedules.forEach { schedule ->
            schedule.item = newItem
        }
    }

    fun reset() {
        resetScore()
        updateDisplayName()
        schedules.forEach { it.item = ItemStack.of(Material.BEDROCK) }
    }

    companion object {
        var teams: List<ItemHuntTeam> = listOf(
            ItemHuntTeam("White Team", NamedTextColor.WHITE),
            ItemHuntTeam("Blue Team", NamedTextColor.BLUE),
            ItemHuntTeam("Red Team", NamedTextColor.RED),
            ItemHuntTeam("Green Team", NamedTextColor.GREEN),
            ItemHuntTeam("Purple Team", NamedTextColor.LIGHT_PURPLE),
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

        val teamSelectionItems = listOf(
            ItemStack.of(Material.WHITE_WOOL),
            ItemStack.of(Material.BLUE_WOOL),
            ItemStack.of(Material.RED_WOOL),
            ItemStack.of(Material.GREEN_WOOL),
            ItemStack.of(Material.PURPLE_WOOL),
            ItemStack.of(Material.CYAN_WOOL),
            ItemStack.of(Material.LIME_WOOL),
            ItemStack.of(Material.YELLOW_WOOL),
            ItemStack.of(Material.ORANGE_WOOL),
        )
    }
}
