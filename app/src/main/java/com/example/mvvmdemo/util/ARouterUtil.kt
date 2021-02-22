package com.example.mvvmdemo.util

import com.alibaba.android.arouter.launcher.ARouter
import com.example.mvvmdemo.constant.RouterActivityPath

object ARouterUtil {

    fun jumpMain() {
        ARouter.getInstance().build(RouterActivityPath.Main.PAGER_MAIN)
            .navigation()
    }

    fun jumpWeb(webUrl: String) {
        ARouter.getInstance().build(RouterActivityPath.Web.PAGER_WEB).withString("webUrl", webUrl)
            .navigation()
    }

    fun jumpArticleList(id: String, title: String) {
        ARouter.getInstance().build(RouterActivityPath.Square.PAGER_SQUARE_LIST)
            .withString("id", id).withString("title", title).navigation()
    }



}