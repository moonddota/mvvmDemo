package com.example.mvvmdemo.ui.mine.my_collect

import com.example.mvvmdemo.bean.ArticleListRes
import com.example.mvvmdemo.network.BaseData
import com.example.mvvmdemo.network.BaseRepository
import com.example.mvvmdemo.network.api.RequestService

class MyCollectRepository : BaseRepository() {

    suspend fun unCollect(id: String): BaseData<Any> = request {
        RequestService.instance.unCollect(id)
    }

    suspend fun listMyCollect(page: Int): BaseData<ArticleListRes> = request {
        RequestService.instance.listMyCollect(page)
    }


}