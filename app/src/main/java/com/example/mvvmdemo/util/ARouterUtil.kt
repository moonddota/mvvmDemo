package com.example.mvvmdemo.util

import com.alibaba.android.arouter.launcher.ARouter
import com.example.mvvmdemo.constant.RouterActivityPath

object ARouterUtil {

    fun jumpMain() {
        ARouter.getInstance().build(RouterActivityPath.Main.PAGER_MAIN)
            .navigation()
    }


}