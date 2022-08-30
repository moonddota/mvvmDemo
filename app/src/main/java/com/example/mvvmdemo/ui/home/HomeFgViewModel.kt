package com.example.mvvmdemo.ui.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.mvvmdemo.Paging3.Repo
import com.example.mvvmdemo.Paging3.RepoPagingSource
import com.example.mvvmdemo.base.BaseViewModel
import com.example.mvvmdemo.bean.ArticleBean
import com.example.mvvmdemo.bean.ArticleListRes
import com.example.mvvmdemo.bean.BannerRes
import com.example.mvvmdemo.network.api.RequestService
import kotlinx.coroutines.flow.Flow

class HomeFgViewModel : BaseViewModel() {
    private val repository = HomeRepository()


    val bannerList = MutableLiveData<List<BannerRes>>()
    fun getBanner() = launchUI(bannerList) {
        val listRes = repository.getBanner()
        listRes.data ?: listOf()
    }

    val listData = MutableLiveData<Flow<PagingData<ArticleBean>>>()
    fun listArticle() = launchUI(listData) {
        Pager(
            config = PagingConfig(pageSize = 10),
            pagingSourceFactory = { ArticlePagingSource(RequestService.instance) }
        ).flow.cachedIn(viewModelScope)
    }


    fun unCollect(id: String) = launchUI {
        repository.unCollect(id)
    }

    fun collect(id: String) = launchUI {
        repository.collect(id)
    }

}