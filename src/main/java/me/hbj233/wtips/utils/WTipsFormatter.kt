package me.hbj233.wtips.utils

import cn.nukkit.Player
import me.hbj233.wtips.WTipsPlugin
import me.hbj233.wtips.placeholder.*
import top.wetabq.easyapi.api.defaults.EconomyAPI
import java.lang.management.ManagementFactory
import java.util.*


internal fun String.getFormattedWTips(player: Player? = null) : String {
    var final : String = this

    val server = WTipsPlugin.instance.server
    val runtime = Runtime.getRuntime()
    val calendar = Calendar.getInstance()
    val year = calendar.get(Calendar.YEAR).toString()
    val month = calendar.get(Calendar.MONTH).plus(1).toString()
    val day = calendar.get(Calendar.DAY_OF_MONTH).toString()
    val date = "${year}年${month}月${day}日"
    val hour = calendar.get(Calendar.HOUR_OF_DAY).toString()
    val minute = calendar.get(Calendar.MINUTE).toString()
    val second = calendar.get(Calendar.SECOND).toString()
    val time = "${hour}时${minute}分${second}秒"

    player?.let { p ->
        val displayGamemode = when (player.gamemode) {
            0 -> "生存"
            1 -> "创造"
            2 -> "冒险"
            3 -> "旁观"
            else -> "未知"
        }
        val handItem = player.inventory.itemInHand

        final = final.replace(PLAYER_NAME, player.name)
        final = final.replace(PLAYER_DISPLAY_NAME, player.displayName)
        final = final.replace(PLAYER_UUID, player.loginChainData.clientUUID.toString())
        final = final.replace(PLAYER_LEVEL, player.level.folderName)
        final = final.replace(PLAYER_IP, player.address)
        final = final.replace(PLAYER_DEVICE, player.loginChainData.deviceModel)
        final = final.replace(PLAYER_HEALTH, player.health.toString())
        final = final.replace(PLAYER_MAX_HEALTH, player.maxHealth.toString())
        final = final.replace(PLAYER_FOOD_LEVEL, player.foodData.level.toString())
        final = final.replace(PLAYER_MAX_FOOD_LEVEL, player.foodData.maxLevel.toString())
        final = final.replace(PLAYER_DIRECTION, player.direction.toString())
        final = final.replace(PLAYER_POSITION, player.position.toString())
        final = final.replace(PLAYER_EXP, player.experience.toString())
        final = final.replace(PLAYER_EXP_LEVEL, player.experienceLevel.toString())
        final = final.replace(PLAYER_EXP_TO_NEXT, Player.calculateRequireExperience(player.experienceLevel).toString())
        final = final.replace(PLAYER_HAND_ITEM, handItem.name)
        final = final.replace(PLAYER_HAND_ITEM_META, handItem.damage.toString())
        final = final.replace(PLAYER_X, player.x.toString())
        final = final.replace(PLAYER_Y, player.y.toString())
        final = final.replace(PLAYER_Z, player.z.toString())
        final = final.replace(PLAYER_GAMEMODE, displayGamemode)
        final = final.replace(PLAYER_OP, player.isOp.toString())
        final = final.replace(PLAYER_PING, player.ping.toString())

        p.speed?.toString()?.let { it1 ->
            final = final.replace(PLAYER_SPEED, it1)
        }

        if (EconomyAPI.compatibilityCheck.isCompatible()) {
            final = final.replace(PLAYER_MONEY, EconomyAPI.getMoney(player).toString())
        }
    }

    final = final.replace(SERVER_ONLINE_PLAYER, server.onlinePlayers.size.toString())
    final = final.replace(SERVER_MAX_PLAYER, server.maxPlayers.toString())
    final = final.replace(SERVER_MOTD, server.motd)
    final = final.replace(SERVER_RAM_USED, (runtime.maxMemory() - runtime.freeMemory()).toString())
    final = final.replace(SERVER_RAM_FREE, runtime.freeMemory().toString())
    final = final.replace(SERVER_RAM_TOTAL, runtime.totalMemory().toString())
    final = final.replace(SERVER_CORES, runtime.availableProcessors().toString())
    final = final.replace(SERVER_TPS, server.ticksPerSecond.toString())
    final = final.replace(SERVER_UPTIME, ManagementFactory.getRuntimeMXBean().uptime.toString())
    final = final.replace(DATE, date)
    final = final.replace(DATE_YEAR, year)
    final = final.replace(DATE_MONTH, month)
    final = final.replace(DATE_DAY, day)
    final = final.replace(TIME, time)
    final = final.replace(TIME_HOUR, hour)
    final = final.replace(TIME_MINUTE, minute)
    final = final.replace(TIME_SECOND, second)
    return final
}