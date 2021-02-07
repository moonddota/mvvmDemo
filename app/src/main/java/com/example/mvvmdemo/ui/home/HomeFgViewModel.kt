package com.example.mvvmdemo.ui.home

import androidx.lifecycle.MutableLiveData
import com.example.mvvmdemo.base.BaseViewModel
import com.example.mvvmdemo.bean.ArticleListRes
import com.example.mvvmdemo.bean.BannerRes
import com.example.mvvmdemo.network.BaseData
import com.example.mvvmdemo.network.api.RequestService
import kotlinx.coroutines.delay

class HomeFgViewModel : BaseViewModel() {
    private val repository = HomeRepository()


    val bannerList = MutableLiveData<List<BannerRes>>()
    fun getBanner() = launchUI(bannerList) {
        val listRes = repository.getBanner()
        listRes.data ?: listOf()
    }

    val listData = MutableLiveData<Pair<ArticleListRes?, Boolean>>()
    fun listArticle(page: Int, isLoadMore: Boolean) = launchUI(listData) {
        val listRes = repository.listArticle(page)
        Pair(listRes.data, isLoadMore)
    }

    fun unCollect(id: String) = launchUI {
        repository.unCollect(id)
    }

    fun collect(id: String) = launchUI {
        repository.collect(id)
    }

}