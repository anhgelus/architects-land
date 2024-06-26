package world.anhgelus.architectsland.bedwars

import org.bukkit.plugin.java.JavaPlugin
import world.anhgelus.architectsland.bedwars.utils.config.ConfigAPI
import java.util.logging.Logger

class Bedwars : JavaPlugin() {

    override fun onEnable() {
        setLogger(logger)
        ConfigAPI.setup(this)
        logger.info("Bedwars Plugin Enabled")
    }

    override fun onDisable() {
        logger.info("Bedwars Plugin Disabled")
    }

    companion object {
        lateinit var logger: Logger

        private fun setLogger(logger: Logger) {
            this.logger = logger
        }
    }
}