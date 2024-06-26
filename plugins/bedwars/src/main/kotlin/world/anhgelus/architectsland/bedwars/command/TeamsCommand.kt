package world.anhgelus.architectsland.bedwars.command

import org.bukkit.Bukkit
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.command.TabCompleter
import world.anhgelus.architectsland.bedwars.game.Game
import world.anhgelus.architectsland.bedwars.team.Team

object TeamsCommand : CommandExecutor, TabCompleter {
    override fun onCommand(
        sender: CommandSender?,
        command: Command?,
        label: String?,
        args: Array<out String>?
    ): Boolean {
        if (label != "start" || args!!.size != 4) return false

        val action = args[1]
        if (action != "add" && action != "remove") return false

        val team = try {
            Team.entries.first {
                it.name.equals(args[2], true)
            }
        } catch (e: NoSuchElementException) {
            sender!!.sendMessage("Team ${args[2]} not found")
            return true
        }
        val player = Bukkit.getPlayer(args[3])
        if (player == null) {
            sender!!.sendMessage("Player ${args[3]} not found")
            return true
        }
        // [add/remove] [teams] [player]
        when (action) {
            "add" -> {
                team.players.add(player)
                Game.instance.teams.add(team)
            }
            "remove" -> {
                team.players.remove(player)
                if (team.players.isEmpty()) {
                    Game.instance.teams.remove(team)
                }
            }
        }
        sender!!.sendMessage("${args[3]} $action to ${team.teamName}")
        return true
    }

    override fun onTabComplete(
        sender: CommandSender?,
        command: Command?,
        alias: String?,
        args: Array<out String>?
    ): MutableList<String> {
        val list = mutableListOf<String>()
        when (args!!.size) {
            1 -> {
                list.add("add")
                list.add("remove")
            }
            2 -> {
                Team.entries.forEach {
                    list.add(it.teamName)
                }
            }
            3 -> {
                Bukkit.getOnlinePlayers().forEach { player ->
                    list.add(player.displayName)
                }
            }
        }
        return list
    }
}