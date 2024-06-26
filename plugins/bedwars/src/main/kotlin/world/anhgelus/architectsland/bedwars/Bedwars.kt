package world.anhgelus.architectsland.bedwars

import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin
import world.anhgelus.architectsland.bedwars.command.BedwarsCommand
import world.anhgelus.architectsland.bedwars.command.StartCommand
import world.anhgelus.architectsland.bedwars.command.StopCommand
import world.anhgelus.architectsland.bedwars.command.TeamsCommand
import world.anhgelus.architectsland.bedwars.events.PlayerListener
import world.anhgelus.architectsland.bedwars.game.Game
import world.anhgelus.architectsland.bedwars.utils.config.ConfigAPI
import java.util.logging.Logger

class Bedwars : JavaPlugin() {

    override fun onEnable() {
        setLogger(logger)
        instance = this
        ConfigAPI.setup(this)

        Game(mutableSetOf())

        val manager = Bukkit.getPluginManager()

        //Listener
        manager.registerEvents(PlayerListener, this)

        //Commands
        this.getCommand("start").executor = StartCommand
        this.getCommand("stop").executor = StopCommand
        this.getCommand("team").executor = TeamsCommand
        this.getCommand("bedwars").executor = BedwarsCommand

        logger.info("Bedwars Plugin Enabled")
    }

    override fun onDisable() {
        logger.info("Bedwars Plugin Disabled")
    }

    companion object {
        lateinit var logger: Logger
            private set
        lateinit var instance : Bedwars
            private set

        private fun setLogger(logger: Logger) {
            this.logger = logger
        }
    }
}