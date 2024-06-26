package world.anhgelus.architectsland.bedwars.events

import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockBreakEvent
import org.bukkit.event.entity.EntityDamageByEntityEvent
import org.bukkit.event.entity.EntityDamageEvent
import world.anhgelus.architectsland.bedwars.Bedwars
import world.anhgelus.architectsland.bedwars.team.Team
import world.anhgelus.architectsland.bedwars.team.TeamPlayer

object PlayerListener : Listener {

    val breakable = listOf(
        Material.WOOL, Material.BED, Material.ENDER_STONE, Material.OBSIDIAN, Material.CLAY, Material.GLASS, Material.WOOD
    )

    @EventHandler
    fun onPlayerDeath(event: EntityDamageEvent) {
        if (event.entity !is Player) {
            return
        }
        val player = event.entity as Player
        if (player.health - event.damage > 0) {
            return
        }
        event.isCancelled = true
        val tp = TeamPlayer.fromPlayer(player)!!
        if (event is EntityDamageByEntityEvent && event.damager is Player) {
            tp.kill(event.damager as Player)
        } else {
            tp.die()
        }

        Bukkit.getScheduler().runTaskLater(Bedwars.instance, {
            tp.respawn()
        }, 5*20L)
    }

    @EventHandler
    fun onBlockBreak(event: BlockBreakEvent) {
        if (event.block.type !in breakable) {
            event.isCancelled = true
            return
        }
        if (event.block.type != Material.BED) {
            return
        }
        val team = Team.getFromBedLocation(event.block.location) ?:
            throw NullPointerException("team from location ${event.block.location} is null")
        team.lostBed()
    }
}