package world.anhgelus.architectsland.bedwars.team

import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.GameMode
import org.bukkit.Material
import org.bukkit.enchantments.Enchantment
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.LeatherArmorMeta
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType
import world.anhgelus.architectsland.bedwars.utils.ColorHelper

data class TeamPlayer(val player: Player, val team: Team) {
    var armorLevel = 0
        private set

    var pickaxeLevel = 0
        private set
    var axeLevel = 0
        private set

    fun respawn() {
        if (!team.canRespawn()) return
        player.teleport(team.respawnLoc)
        @Suppress("DEPRECATION")
        player.sendTitle("${ChatColor.GREEN}Respawned!", "")
        player.gameMode = GameMode.SURVIVAL

        pickaxeLevel--
        axeLevel--

        val inv = player.inventory
        inv.clear()

        val sword = ItemStack(Material.WOOD_SWORD)
        if (team.upgrade.sharpness.level > 0) {
            val meta = sword.itemMeta
            meta.addEnchant(Enchantment.DAMAGE_ALL, team.upgrade.sharpness.level, true)
            sword.itemMeta = meta
        }
        inv.setItem(0, sword)

        val armors = when (armorLevel) {
            1 -> mutableListOf(ItemStack(Material.CHAINMAIL_HELMET), ItemStack(Material.CHAINMAIL_CHESTPLATE))
            2 -> mutableListOf(ItemStack(Material.IRON_HELMET), ItemStack(Material.IRON_CHESTPLATE))
            3 -> mutableListOf(ItemStack(Material.DIAMOND_HELMET), ItemStack(Material.DIAMOND_CHESTPLATE))
            else -> mutableListOf(ItemStack(Material.LEATHER_HELMET), ItemStack(Material.LEATHER_CHESTPLATE))
        }
        val boots = ItemStack(Material.LEATHER_BOOTS)
        var meta = boots.itemMeta as LeatherArmorMeta
        meta.color = ColorHelper.chatColorToColor[team.color]
        boots.itemMeta = meta

        val leggings = ItemStack(Material.LEATHER_LEGGINGS)
        meta = leggings.itemMeta as LeatherArmorMeta
        meta.color = ColorHelper.chatColorToColor[team.color]
        leggings.itemMeta = meta

        armors.add(boots)
        armors.add(leggings)

        if (team.upgrade.protection.level > 0) {
            armors.forEach {
                val meta = it.itemMeta
                meta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, team.upgrade.protection.level, false)
                it.itemMeta = meta
            }
        }

        if (pickaxeLevel > 0) {
            inv.setItem(2, when (pickaxeLevel) {
                1 -> {
                    val stack = ItemStack(Material.WOOD_PICKAXE)
                    val meta = stack.itemMeta
                    meta.addEnchant(Enchantment.DIG_SPEED, 1, false)
                    stack.itemMeta = meta
                    stack
                }
                2 -> {
                    val stack = ItemStack(Material.IRON_PICKAXE)
                    val meta = stack.itemMeta
                    meta.addEnchant(Enchantment.DIG_SPEED, 2, false)
                    stack.itemMeta = meta
                    stack
                }
                3 -> {
                    val stack = ItemStack(Material.GOLD_PICKAXE)
                    val meta = stack.itemMeta
                    meta.addEnchant(Enchantment.DIG_SPEED, 3, false)
                    stack.itemMeta = meta
                    stack
                }
                else -> ItemStack(Material.AIR)
            })
        }
        if (axeLevel > 0) {
            inv.setItem(2, when (axeLevel) {
                1 -> {
                    val stack = ItemStack(Material.WOOD_AXE)
                    val meta = stack.itemMeta
                    meta.addEnchant(Enchantment.DIG_SPEED, 1, false)
                    stack.itemMeta = meta
                    stack
                }
                2 -> {
                    val stack = ItemStack(Material.STONE_AXE)
                    val meta = stack.itemMeta
                    meta.addEnchant(Enchantment.DIG_SPEED, 1, false)
                    stack.itemMeta = meta
                    stack
                }
                3 -> {
                    val stack = ItemStack(Material.IRON_AXE)
                    val meta = stack.itemMeta
                    meta.addEnchant(Enchantment.DIG_SPEED, 2, false)
                    stack.itemMeta = meta
                    stack
                }
                // effi 3
                else -> ItemStack(Material.AIR)
            })
        }

        if (team.upgrade.haste.level > 0) {
            player.addPotionEffect(PotionEffect(PotionEffectType.FAST_DIGGING, 36000, team.upgrade.haste.level-1))
        }
    }

    fun die() {
        dropItems()
        player.gameMode = GameMode.SPECTATOR
        Bukkit.broadcastMessage("${ChatColor.RED}${player.displayName} ${ChatColor.RESET}died")
    }

    fun kill(killer: Player) {
        dropItems()
        player.gameMode = GameMode.SPECTATOR
        Bukkit.broadcastMessage("${ChatColor.RED}${player.displayName} ${ChatColor.RESET}killed by ${killer.displayName}")
    }

    fun dropItems() {
        // drop items on the ground
    }

    companion object {
        fun fromPlayer(player: Player): TeamPlayer? {
            Team.entries.forEach {
                if (player in it.players) {
                    return TeamPlayer(player, it)
                }
            }
            return null
        }
    }
}
