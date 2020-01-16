package com.example.mvvmdemo

import android.app.Activity
import android.app.Application
import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import com.blankj.utilcode.util.Utils
import com.example.mvvmdemo.util.SharePreUtil
import com.jeremyliao.liveeventbus.LiveEventBus
import com.qmuiteam.qmui.arch.QMUISwipeBackActivityManager

class App : Application() {


    companion object {
        lateinit var instance: App

        private var activityTasks: MutableList<Activity> = mutableListOf()

        fun getTopActivity(): Activity? {
            var mBaseActivity: Activity? = null
            synchronized(activityTasks) {
                val size = activityTasks.size - 1
                if (size < 0) {
                    return null
                }
                mBaseActivity = activityTasks.get(size)
            }
            return mBaseActivity
        }
    }


    @RequiresApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    override fun onCreate() {
        super.onCreate()

        instance = this

        SharePreUtil.init(this, "User")

        QMUISwipeBackActivityManager.init(this)

        Utils.init(this)

        initActivityLifecycle()

        LiveEventBus.config()
    }


    /**
     * 初始化activity生命周期回调，可以处理全局事件
     */
    @RequiresApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    private fun initActivityLifecycle() {
        registerActivityLifecycleCallbacks(mActivityLifecycleCallbacks)
    }

    /**
     * 默认Activity生命周期回调函数，可用于添加全局行为
     */
    private val mActivityLifecycleCallbacks = object : Application.ActivityLifecycleCallbacks {

        override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
            activityTasks.add(activity)
        }

        override fun onActivityStarted(activity: Activity) {
        }

        override fun onActivityResumed(activity: Activity) {

        }

        override fun onActivityPaused(activity: Activity) {

        }

        override fun onActivityStopped(activity: Activity) {

        }

        override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {

        }

        override fun onActivityDestroyed(activity: Activity) {
            activityTasks.remove(activity)
        }
    }

}

