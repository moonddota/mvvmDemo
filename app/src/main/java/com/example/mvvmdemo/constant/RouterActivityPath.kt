package com.example.mvvmdemo.constant


class RouterActivityPath {

    /**
     * Main组件
     */
    object Main {
        /**
         * 主页面
         */
        const val PAGER_MAIN = "/pager/main"
    }


    /**
     * web 组件
     */
    object Web {
        const val WEB = "/module_web"
        const val PAGER_WEB = "${WEB}/Web"
    }

    object Square {
        const val SQUARE = "/module_square"
        const val PAGER_SQUARE_LIST = "${SQUARE}/square_list"
    }

    object Setting {
        const val SETTING = "/pager/setting"
    }

    object LanguageSet {
        const val LANGUAGESET = "/pager/LanguageSet"
    }

    object AboutUs {
        const val ABOUTUS = "/pager/AboutUs"
    }

    object ScoreRankList {
        const val SCORERANKLISTAC = "/ScoreRankList/AC"
    }

    object MyScore {
        const val MYSCOREAC = "/MyScore/Ac"
    }

    object MyCollect {
        const val MyCollectAc = "/MyCollect/Ac"
    }

    object MyShare {
        const val MyShareAc = "/MyShare/Ac"
    }

    object ShareArticle {
        const val ShareArticleAc = "/ShareArticle/Ac"
    }

    object OpenSource {
        const val OpenSourceAc = "/OpenSource/Ac"
    }

    object Login {
        const val LoginAc = "/module_login/Login"
    }

    object Paging3 {
        const val Paging3Ac = "/Paging3/Ac"
    }

    object Camera_1{
        const val Camera_1 = "/Camera1"
        const val Ac = "${Camera_1}/AC"
    }

}