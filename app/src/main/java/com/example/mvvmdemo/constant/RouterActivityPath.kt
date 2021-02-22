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
        const val PAGER_WEB = WEB + "/Web"
    }

    object Square {
        const val SQUARE = "/module_square"
        const val PAGER_SQUARE_LIST = SQUARE + "/square_list"
    }

}