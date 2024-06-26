package world.anhgelus.architectsland.bedwars.utils.config

import world.anhgelus.architectsland.bedwars.Bedwars

object ConfigAPI {
    private var main: Bedwars? = null

    fun getConfig(name: String): Config {
        return Config(main!!, name)
    }

    fun setup(main: Bedwars) {
        ConfigAPI.main = main
    }
}