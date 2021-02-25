package com.example.mvvmdemo.ui.mine.share_article

import com.example.mvvmdemo.network.BaseData
import com.example.mvvmdemo.network.BaseRepository
import com.example.mvvmdemo.network.api.RequestService

class ShareArticleRepository:BaseRepository() {

    suspend fun shareArticle(title: String?, link: String?): BaseData<Any> = request {
        RequestService.instance.shareArticle(title,link)
    }


}