package com.example.mvvmdemo.ui.publics

import androidx.lifecycle.MutableLiveData
import com.example.mvvmdemo.base.BaseViewModel
import com.example.mvvmdemo.bean.ArticleListRes
import com.example.mvvmdemo.bean.PublicAuthorListRes

class PublicVM : BaseViewModel() {

    private val repository = PublicRepository()

    fun unCollect(id: String) = launchUI {
        repository.unCollect(id)
    }

    fun collect(id: String) = launchUI {
        repository.collect(id)
    }

    val data = MutableLiveData<Pair<ArticleListRes?, Boolean>>()
    fun getList(id: String, page: Int, isMore: Boolean) = launchUI(data) {
        val listRes = repository.listArticle(id, page)
        Pair(listRes.data, isMore)
    }

    val topData = MutableLiveData<List<PublicAuthorListRes>>()
    fun getTopData() = launchUI (topData){
        val listRes = repository.listPublicAuthor()
        listRes.data?: listOf()
    }
}