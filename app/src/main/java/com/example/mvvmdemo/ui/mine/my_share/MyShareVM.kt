package com.example.mvvmdemo.ui.mine.my_share

import androidx.lifecycle.MutableLiveData
import com.example.mvvmdemo.base.BaseViewModel
import com.example.mvvmdemo.bean.ArticleListRes
import com.example.mvvmdemo.bean.MyShareBean

class MyShareVM : BaseViewModel() {


    private val repository = MyShareRepository()

    fun unCollect(id: String) = launchUI {
        repository.unCollect(id)
    }

    fun collect(id: String) = launchUI {
        repository.collect(id)
    }

    val data = MutableLiveData<Pair<MyShareBean?, Boolean>>()
    fun getList(page: Int, isMore: Boolean) = launchUI(data) {
        val res = repository.listMyShare(page)
        Pair(res.data, isMore)
    }


    fun deleteArticle(id: String) = launchUI {
        repository.deleteArticle(id)
    }

}