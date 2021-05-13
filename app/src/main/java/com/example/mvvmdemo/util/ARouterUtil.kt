package com.example.mvvmdemo.util

import com.alibaba.android.arouter.launcher.ARouter
import com.example.mvvmdemo.R
import com.example.mvvmdemo.constant.RouterActivityPath

object ARouterUtil {

    fun jumpMain() {
        ARouter.getInstance().build(RouterActivityPath.Main.PAGER_MAIN)
            .withTransition(R.anim.slide_in_right, R.anim.slide_out_right)
            .navigation()
    }

    fun jumpWeb(webUrl: String) {
        ARouter.getInstance().build(RouterActivityPath.Web.PAGER_WEB)
            .withString("webUrl", webUrl)
            .navigation()
    }

    fun jumpArticleList(id: String, title: String) {
        ARouter.getInstance().build(RouterActivityPath.Square.PAGER_SQUARE_LIST)
            .withString("id", id)
            .withString("title", title)
            .navigation()
    }

    fun jumpSetting() {
        ARouter.getInstance().build(RouterActivityPath.Setting.SETTING)
            .navigation()
    }


    fun jumpLanguageSet() {
        ARouter.getInstance().build(RouterActivityPath.LanguageSet.LANGUAGESET)
            .navigation()
    }

    fun jumpAboutUs() {
        ARouter.getInstance().build(RouterActivityPath.AboutUs.ABOUTUS)
            .navigation()
    }

    fun jumpScoreRankListAc() {
        ARouter.getInstance().build(RouterActivityPath.ScoreRankList.SCORERANKLISTAC)
            .navigation()
    }


    fun jumpMyScoreAc() {
        ARouter.getInstance().build(RouterActivityPath.MyScore.MYSCOREAC)
            .navigation()
    }


    fun jumpMyCollectAc() {
        ARouter.getInstance().build(RouterActivityPath.MyCollect.MyCollectAc)
            .navigation()
    }


    fun jumpMyShareAc() {
        ARouter.getInstance().build(RouterActivityPath.MyShare.MyShareAc)
            .navigation()
    }


    fun jumpShareArticleAc() {
        ARouter.getInstance().build(RouterActivityPath.ShareArticle.ShareArticleAc)
            .navigation()
    }


    fun jumpOpenSourceAc() {
        ARouter.getInstance().build(RouterActivityPath.OpenSource.OpenSourceAc)
            .navigation()
    }

    fun jumpLogin() {
        ARouter.getInstance().build(RouterActivityPath.Login.LoginAc)
            .navigation()
    }

}