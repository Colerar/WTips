package me.hbj233.wtips.module

import cn.nukkit.Player
import cn.nukkit.level.Level
import me.hbj233.wtips.WTipsPlugin
import me.hbj233.wtips.data.LevelData
import me.hbj233.wtips.placeholder.*
import me.hbj233.wtips.placeholder.SERVER_MAX_PLAYER
import me.hbj233.wtips.placeholder.SERVER_ONLINE_PLAYER
import me.hbj233.wtips.utils.getFormattedWTips
import top.wetabq.easyapi.api.defaults.*
import top.wetabq.easyapi.config.defaults.SimpleConfigEntry
import top.wetabq.easyapi.config.encoder.advance.SimpleCodecEasyConfig
import top.wetabq.easyapi.module.ModuleInfo
import top.wetabq.easyapi.module.ModuleVersion
import top.wetabq.easyapi.module.SimpleEasyAPIModule
import top.wetabq.easyapi.module.defaults.ScreenShowModule
import top.wetabq.easyapi.screen.ScreenShow
import top.wetabq.easyapi.screen.ShowType
import top.wetabq.easyapi.utils.color

object WTipsModule : SimpleEasyAPIModule(){

    private const val MODULE_NAME = "WTips"
    private const val AUTHOR = "HBJ"
    const val SIMPLE_CONFIG = "wtipsSimpleConfig"
    const val WTIPS_LEVEL_CONFIG = "wtipsLevelConfig"
    const val TIPS_PATH = ".tips"
    const val TASK_PATH = "$TIPS_PATH.task"
    const val TASK_DELAY_PATH = "$TASK_PATH.delay"
    const val TASK_DURATION_PATH = "$TASK_PATH.duration"
    const val SHOW_TYPE_PATH = "$TASK_PATH.showtype"

    const val WTIPS_LEVEL_CONFIG_NAME = "wtipsLevelConfig"
    lateinit var wtipsLevelConfig : SimpleCodecEasyConfig<LevelData>

    private var tips = "§9❀地图>$PLAYER_LEVEL\n" +
            " §f❀在线>$SERVER_ONLINE_PLAYER/$SERVER_MAX_PLAYER §b❀延迟>$PLAYER_PING \n" +
            " §e❀金币>$PLAYER_MONEY §7❀时间>$TIME "
    private var taskDelay : Int = 20
    private var durationTime : Int = 20
    private var showtype : String = "TIP"

    override fun getModuleInfo(): ModuleInfo = ModuleInfo(
            WTipsPlugin.instance,
            MODULE_NAME,
            AUTHOR,
            ModuleVersion(1, 0, 0)
    )

    override fun moduleRegister() {

        val simpleConfig = this.registerAPI(SIMPLE_CONFIG, SimpleConfigAPI(WTipsPlugin.instance))
                .add(SimpleConfigEntry(TIPS_PATH, tips))
                .add(SimpleConfigEntry(TASK_DELAY_PATH, taskDelay))
                .add(SimpleConfigEntry(TASK_DURATION_PATH, durationTime))
                .add(SimpleConfigEntry(SHOW_TYPE_PATH, showtype))

        tips = simpleConfig.getPathValue(TIPS_PATH) as String? ?: tips
        taskDelay = simpleConfig.getPathValue(TASK_DELAY_PATH).toString().toInt()
        durationTime = simpleConfig.getPathValue(TASK_DURATION_PATH).toString().toInt()
        showtype = when(simpleConfig.getPathValue(SHOW_TYPE_PATH) as String?){
            "POPUP" -> "POPUP"
            "TIP" -> "TIP"
            else -> "TIP"
        }

        wtipsLevelConfig = object : SimpleCodecEasyConfig<LevelData>(WTIPS_LEVEL_CONFIG_NAME, WTipsPlugin.instance, LevelData::class.java, LevelData()){}

        WTipsPlugin.instance.server.levels.values.fold(mutableListOf<String>(), { acc, level ->
            if (!wtipsLevelConfig.simpleConfig.containsKey(level.folderName)){
                acc.add(level.folderName)
            }
            acc
        })

        this.registerAPI(WTIPS_LEVEL_CONFIG, ConfigAPI())
                .add(wtipsLevelConfig)

        MessageFormatAPI.registerSimplePlayerFormatter { msg, player ->
            msg.getFormattedWTips(player)
        }

        MessageFormatAPI.registerSimpleFormatter(object : SimpleMessageFormatter {
            override fun format(message: String): String = message.getFormattedWTips(null)
        })


        SimplePluginTaskAPI.delayRepeating(taskDelay, taskDelay) { _, _ ->
            WTipsPlugin.instance.server.levels.values.fold(arrayListOf<Level>() , { acc, level ->
                if (wtipsLevelConfig.safeGetData(level.folderName).canShowWTips) {
                    acc.add(level)
                }
                acc
            }).fold(arrayListOf<Player>(), { acc, level ->
                level.players.forEach {
                    acc.add(it.value)
                }
                acc
            }).forEach {
                val showType = when(showtype) {
                    "TIP" -> ShowType.TIP
                    "POPUP" -> ShowType.POPUP
                    else -> ShowType.TIP
                }
                val formatted = MessageFormatAPI.format(tips, it, it.name)
                ScreenShowModule.addScreenShow(ScreenShow(
                        targetPlayers = setOf(it),
                        showMessage = formatted.color(),
                        showPriority = ScreenShowModule.LOWEST_PRIORITY,
                        durationTime = durationTime,
                        afterOccupyDurationTime = 20,
                        showType = showType
                ))
            }


        }

    }




    override fun moduleDisable() {}


}