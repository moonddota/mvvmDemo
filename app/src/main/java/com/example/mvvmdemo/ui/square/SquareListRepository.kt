package com.example.mvvmdemo.ui.square

import com.example.mvvmdemo.bean.ArticleListRes
import com.example.mvvmdemo.network.BaseData
import com.example.mvvmdemo.network.BaseRepository
import com.example.mvvmdemo.network.api.RequestService

class SquareListRepository : BaseRepository() {

    suspend fun unCollect(id: String): BaseData<Any> = request {
        RequestService.instance.unCollect(id)
    }

    suspend fun collect(id: String): BaseData<Any> = request {
        RequestService.instance.collect(id)
    }

    suspend fun listArticle(page: Int, id: String): BaseData<ArticleListRes> = request {
        RequestService.instance.listArticle(page, id)
    }

}