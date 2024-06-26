package world.anhgelus.architectsland.bedwars

import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin
import world.anhgelus.architectsland.bedwars.events.PlayerListener
import world.anhgelus.architectsland.bedwars.utils.config.ConfigAPI
import java.util.logging.Logger

class Bedwars : JavaPlugin() {

    override fun onEnable() {
        setLogger(logger)
        instance = this
        ConfigAPI.setup(this)

        logger.info("Bedwars Plugin Enabled")

        val manager = Bukkit.getPluginManager()
        manager.registerEvents(PlayerListener, this)
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