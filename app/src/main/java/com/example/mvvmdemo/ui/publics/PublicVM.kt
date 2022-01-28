package com.example.mvvmdemo.ui.publics

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.mvvmdemo.base.BaseViewModel
import com.example.mvvmdemo.bean.ArticleBean
import com.example.mvvmdemo.bean.PublicAuthorListRes
import com.example.mvvmdemo.network.api.RequestService
import kotlinx.coroutines.flow.Flow

class PublicVM : BaseViewModel() {

    private val repository = PublicRepository()

    fun unCollect(id: String) = launchUI {
        repository.unCollect(id)
    }

    fun collect(id: String) = launchUI {
        repository.collect(id)
    }

    val data = MutableLiveData<Flow<PagingData<ArticleBean>>>()
    fun listArticle(id: String) = launchUI(data) {
        Pager(
            config = PagingConfig(pageSize = 10),
            pagingSourceFactory = { PublicPagingSource(RequestService.instance,id) }
        ).flow.cachedIn(viewModelScope)
    }



    val topData = MutableLiveData<List<PublicAuthorListRes>>()
    fun getTopData() = launchUI (topData){
        val listRes = repository.listPublicAuthor()
        listRes.data?: listOf()
    }
}