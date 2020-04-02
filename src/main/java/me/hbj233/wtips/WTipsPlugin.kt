package me.hbj233.wtips

import cn.nukkit.plugin.PluginBase
import me.hbj233.wtips.module.WTipsModule
import top.wetabq.easyapi.module.EasyAPIModuleManager

class WTipsPlugin : PluginBase() {

    override fun onEnable() {
        instance = this
        EasyAPIModuleManager.register(WTipsModule)
    }


    companion object {
        lateinit var instance : WTipsPlugin
    }



}
