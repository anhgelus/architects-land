package world.anhgelus.architectsland.bedwars.utils

import org.bukkit.ChatColor
import org.bukkit.Color

object ColorHelper {
    val chatColorToColor = mapOf<ChatColor, Color>(
        ChatColor.AQUA to Color.AQUA,
        ChatColor.YELLOW to Color.YELLOW,
        ChatColor.RED to Color.RED,
        ChatColor.GREEN to Color.GREEN,
        ChatColor.BLUE to Color.BLUE,
        ChatColor.GRAY to Color.GRAY,
        ChatColor.LIGHT_PURPLE to Color.FUCHSIA,
        ChatColor.DARK_PURPLE to Color.PURPLE,
    )
}