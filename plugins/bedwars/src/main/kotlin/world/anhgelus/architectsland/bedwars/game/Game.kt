package world.anhgelus.architectsland.bedwars.game

import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.inventory.ItemStack
import world.anhgelus.architectsland.bedwars.Bedwars
import world.anhgelus.architectsland.bedwars.team.Team

class Game(
    val teams: MutableSet<Team>,
) {
    init {
        instance = this
    }

    private var taskId: Int? = null

    private var second = 0

    fun start() {
        teams.forEach { team ->
            team.players.forEach { player ->
                player.teleport(team.respawnLoc)
            }
        }
        // start each second

        taskId = Bukkit.getScheduler().scheduleSyncRepeatingTask(Bedwars.instance, {
            eachSecond()
        }, 0L, 20L)
    }

    fun stop() {
        // stop each second
        Bukkit.getScheduler().cancelTask(taskId!!)
    }

    private fun eachSecond() {
        teams.forEach { team ->
            val loc = team.generatorLoc!!
            val lvl = team.upgrade.forge.level
            when (lvl) {
                0 -> {
                    loc.world.dropItemNaturally(loc, ItemStack(Material.IRON_INGOT))
                    if (second%4 == 3) {
                        loc.world.dropItemNaturally(loc, ItemStack(Material.GOLD_INGOT))
                    }
                }
                1 -> {
                    loc.world.dropItemNaturally(loc, ItemStack(Material.IRON_INGOT))
                    if (second%4 == 1) {
                        loc.world.dropItemNaturally(loc, ItemStack(Material.IRON_INGOT))
                    } else if (second%4 == 3) {
                        loc.world.dropItemNaturally(loc, ItemStack(Material.GOLD_INGOT))
                        loc.world.dropItemNaturally(loc, ItemStack(Material.IRON_INGOT))
                    }
                    if (second%8 == 7) {
                        loc.world.dropItemNaturally(loc, ItemStack(Material.GOLD_INGOT))
                    }
                }
                2 -> {
                    val iron = ItemStack(Material.IRON_INGOT)
                    iron.amount = 2
                    loc.world.dropItemNaturally(loc, iron)
                    if (second%2 == 1) {
                        loc.world.dropItemNaturally(loc, ItemStack(Material.GOLD_INGOT))
                    }
                    if (second%60 == 0) {
                        loc.world.dropItemNaturally(loc, ItemStack(Material.EMERALD))
                    }
                }
                3 -> {
                    val iron = ItemStack(Material.IRON_INGOT)
                    iron.amount = 4
                    loc.world.dropItemNaturally(loc, iron)
                    loc.world.dropItemNaturally(loc, ItemStack(Material.GOLD_INGOT))
                    if (second%30 == 0) {
                        loc.world.dropItemNaturally(loc, ItemStack(Material.EMERALD))
                    }
                }
            }
        }
        second++
    }

    companion object {
        lateinit var instance: Game
            private set
    }
}