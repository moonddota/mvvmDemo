package com.example.mvvmdemo

import android.app.Application
import com.alibaba.android.arouter.launcher.ARouter
import com.blankj.utilcode.util.Utils
import com.jeremyliao.liveeventbus.LiveEventBus
import com.tencent.mmkv.MMKV
import kotlin.properties.Delegates


class App : Application() {

    companion object {
        @JvmStatic
        var instance: App by Delegates.notNull()
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        Utils.init(this)
        LiveEventBus.config()
        initArouter()
        //初始化mmkv
        MMKV.initialize(this)
    }

    /**
     * ARouter的初始化
     */
    private fun initArouter() {
        //测试环境
        if (BuildConfig.DEBUG) {
            ARouter.openLog() // 打印日志
            ARouter.openDebug() // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
        }
        ARouter.init(this)
    }

}