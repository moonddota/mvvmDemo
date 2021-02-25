package com.example.mvvmdemo.ui.mine.my_share

import com.example.mvvmdemo.bean.ArticleListRes
import com.example.mvvmdemo.bean.MyShareBean
import com.example.mvvmdemo.network.BaseData
import com.example.mvvmdemo.network.BaseRepository
import com.example.mvvmdemo.network.api.RequestService

class MyShareRepository : BaseRepository() {

    suspend fun unCollect(id: String): BaseData<Any> = request {
        RequestService.instance.unCollect(id)
    }

    suspend fun collect(id: String): BaseData<Any> = request {
        RequestService.instance.collect(id)
    }

    suspend fun listMyShare(page: Int): BaseData<MyShareBean> = request {
        RequestService.instance.listMyShare(page)
    }

    suspend fun deleteArticle(id: String): BaseData<Any> = request {
        RequestService.instance.deleteArticle(id)
    }


}