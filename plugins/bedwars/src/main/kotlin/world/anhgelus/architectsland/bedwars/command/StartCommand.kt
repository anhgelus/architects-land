package world.anhgelus.architectsland.bedwars.command

import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import world.anhgelus.architectsland.bedwars.game.Game

object StartCommand : CommandExecutor {
    override fun onCommand(
        sender: CommandSender?,
        command: Command?,
        label: String?,
        args: Array<out String>?
    ): Boolean {
        if (label != "start" || args!!.size > 1) return false
        Game.instance.start()
        return true
    }
}