package world.anhgelus.architectsland.bedwars.team

import org.bukkit.ChatColor
import org.bukkit.Location
import org.bukkit.configuration.ConfigurationSection
import org.bukkit.entity.Player
import world.anhgelus.architectsland.bedwars.utils.LocationHelper

enum class Team(
    val teamName: String,
    val color: ChatColor
) {
    RED("Red", ChatColor.RED),
    GREEN("Green", ChatColor.GREEN),
    BLUE("Blue", ChatColor.BLUE),
    YELLOW("Yellow", ChatColor.YELLOW),
    AQUA("Aqua", ChatColor.AQUA),
    GREY("Grey", ChatColor.GRAY),
    PINK("Pink", ChatColor.LIGHT_PURPLE),
    PURPLE("Pink", ChatColor.DARK_PURPLE);

    var hasBed: Boolean = true
        private set
    val players: MutableSet<Player> = mutableSetOf()

    var respawnLoc: Location? = null
    var bedLoc: Location? = null
    var generatorLoc: Location? = null

    var itemSellerLoc: Location? = null
    var upgradeSellerLoc: Location? = null

    val upgrade: TeamUpgrade = TeamUpgrade()

    fun lostBed() {
        hasBed = false
        players.forEach {
            @Suppress("DEPRECATION")
            it.sendTitle("${ChatColor.RED}Bed destroyed!", "")
        }
    }

    fun canRespawn(): Boolean {
        return hasBed
    }

    fun setInConfig(section: ConfigurationSection) {
        section.set("color", this.color.toString())
        section.set("name", this.teamName)
        LocationHelper.setInConfig(this.respawnLoc!!, section.getConfigurationSection("location.respawn"))
        LocationHelper.setInConfig(this.bedLoc!!, section.getConfigurationSection("location.bed"))
        LocationHelper.setInConfig(this.generatorLoc!!, section.getConfigurationSection("location.generator"))
        LocationHelper.setInConfig(this.itemSellerLoc!!, section.getConfigurationSection("location.seller.item"))
        LocationHelper.setInConfig(this.upgradeSellerLoc!!, section.getConfigurationSection("location.seller.upgrade"))
    }

    private fun updateLocation(respawnLoc: Location, bedLoc: Location, generatorLoc: Location, itemSellerLoc: Location, upgradeSellerLoc: Location) {
        this.respawnLoc = respawnLoc
        this.bedLoc = bedLoc
        this.generatorLoc = generatorLoc
        this.itemSellerLoc = itemSellerLoc
        this.upgradeSellerLoc = upgradeSellerLoc
    }

    companion object {
        fun loadFromConfig(section: ConfigurationSection): Team? {
            val color = ChatColor.valueOf(section.getString("color")!!)
            val name = section.getString("name")
            // location
            val respawnLoc = LocationHelper.loadFromConfig(section.getConfigurationSection("location.respawn"))
            val bedLoc = LocationHelper.loadFromConfig(section.getConfigurationSection("location.bed"))
            val generatorLoc = LocationHelper.loadFromConfig(section.getConfigurationSection("location.generator"))
            val itemSellerLoc = LocationHelper.loadFromConfig(section.getConfigurationSection("location.seller.item"))
            val upgradeSellerLoc = LocationHelper.loadFromConfig(section.getConfigurationSection("location.seller.upgrade"))

            return try {
                val f = entries.first {
                    it.color == color && it.name == name
                }
                f.updateLocation(respawnLoc, bedLoc, generatorLoc, itemSellerLoc, upgradeSellerLoc)
                f
            } catch (e: NoSuchElementException) {
                null
            }
        }

        fun getFromBedLocation(location: Location): Team? {
            return try {
                entries.first {
                    it.bedLoc == location
                }
            } catch (e: NoSuchElementException) {
                null
            }
        }
    }
}