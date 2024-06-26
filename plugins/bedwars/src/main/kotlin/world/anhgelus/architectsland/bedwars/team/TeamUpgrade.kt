package world.anhgelus.architectsland.bedwars.team

class TeamUpgrade {
    enum class Upgradable(val max: Int) {
        SHARPNESS(2),
        PROTECTION(4),
        FORGE(3),
        HASTE(2),
        REGENERATION(1);
    }

    data class Upgrade(
        val upgradable: Upgradable,
        var level: Int = 0
    ) {
        fun nextLevel(): Boolean {
            if (level >= upgradable.max) {
                return false
            }
            level++
            return true
        }
    }

    val sharpness = Upgrade(Upgradable.SHARPNESS)
    val protection = Upgrade(Upgradable.PROTECTION)
    val forge = Upgrade(Upgradable.FORGE)
    val haste = Upgrade(Upgradable.HASTE)
    val regeneration = Upgrade(Upgradable.REGENERATION)
    val traps = arrayOf<Int>()
}
