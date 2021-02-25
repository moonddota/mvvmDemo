package com.example.mvvmdemo.ui.home

import com.example.mvvmdemo.bean.ArticleListRes
import com.example.mvvmdemo.bean.BannerRes
import com.example.mvvmdemo.network.BaseData
import com.example.mvvmdemo.network.BaseRepository
import com.example.mvvmdemo.network.api.RequestService

class HomeRepository : BaseRepository() {

    suspend fun getBanner(): BaseData<List<BannerRes>> = request {
        RequestService.instance.getBanner()
    }

    suspend fun listArticle(page: Int): BaseData<ArticleListRes> = request {
        RequestService.instance.listArticle(page)
    }

    suspend fun unCollect(id: String): BaseData<Any> = request {
        RequestService.instance.unCollect(id)
    }


    suspend fun collect(id: String): BaseData<Any> = request {
        RequestService.instance.collect(id)
    }


}