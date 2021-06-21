package com.example.mvvmdemo.bean

import com.example.mvvmdemo.Paging3.DifferData

data class RankBean(
    var coinCount: String? = null,

    val date: Long? = null,

    val desc: String? = null,

    val id: String? = null,

    val reason: String? = null,

    val userId: String? = null,

    val userName: String? = null,

    val username: String? = null,

    val type: Int? = null,

    val rank: String? = null,

    val level: Int? = null
) : DifferData {
}