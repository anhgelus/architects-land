package world.anhgelus.architectsland.bedwars.utils

import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.configuration.ConfigurationSection
import world.anhgelus.architectsland.bedwars.utils.config.InvalidConfigException
import java.util.*

object LocationHelper {
    fun setInConfig(location: Location, section: ConfigurationSection) {
        section.set("world", location.world.uid.toString())
        section.set("x", location.x)
        section.set("y", location.y)
        section.set("z", location.z)
        section.set("yaw", location.yaw.toDouble())
        section.set("pitch", location.pitch.toDouble())
    }

    fun loadFromConfig(section: ConfigurationSection): Location {
        val worldId = UUID.fromString(section.getString("world"))
        val world = Bukkit.getWorld(worldId) ?: throw InvalidConfigException("World $worldId not found")
        val x = section.getDouble("x")
        val y = section.getDouble("y")
        val z = section.getDouble("z")
        val yaw = section.getDouble("yaw").toFloat()
        val pitch = section.getDouble("pitch").toFloat()
        return Location(world, x, y, z, yaw, pitch)
    }
}