package com.example.mvvmdemo.bean

data class UserInfo(
    var email: String? = null,
    var icon: String? = null,
    var id: String? = null,
    var password: String? = null,
    var token: String? = null,
    var type: String? = null,
    var username: String? = null,
    var chapterTops: List<*>? = null,
    var collectIds: List<Int>? = null,//收藏的文章id

    /**********************************************我的积分 */
    var coinCount: String? = null,
    var level: Int? = null,
    var rank: String? = null,
    var userId: String? = null,
    var reason: String? = null,
    var desc: String? = null,
    var date: String? = null

) {
}