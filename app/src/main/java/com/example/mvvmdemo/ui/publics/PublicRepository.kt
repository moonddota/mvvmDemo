package com.example.mvvmdemo.ui.publics

import com.example.mvvmdemo.bean.ArticleListRes
import com.example.mvvmdemo.bean.PublicAuthorListRes
import com.example.mvvmdemo.network.BaseData
import com.example.mvvmdemo.network.BaseRepository
import com.example.mvvmdemo.network.api.RequestService

class PublicRepository : BaseRepository() {

    suspend fun unCollect(id: String): BaseData<Any> = request {
        RequestService.instance.unCollect(id)
    }

    suspend fun collect(id: String): BaseData<Any> = request {
        RequestService.instance.collect(id)
    }

    suspend fun listArticle(id: String, page: Int): BaseData<ArticleListRes> = request {
        RequestService.instance.listArticle(id, page)
    }

    suspend fun listPublicAuthor(): BaseData<List<PublicAuthorListRes>> = request {
        RequestService.instance.listPublicAuthor()
    }


}