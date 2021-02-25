package com.example.mvvmdemo.ui.mine.my_collect

import androidx.lifecycle.MutableLiveData
import com.example.mvvmdemo.base.BaseViewModel
import com.example.mvvmdemo.bean.ArticleListRes

class MyCollectVM : BaseViewModel() {

    private val repository = MyCollectRepository()

    fun unCollect(id: String) = launchUI {
        repository.unCollect(id)
    }


    val list = MutableLiveData<Pair<ArticleListRes?, Boolean>>()
    fun getList(page: Int, isMore: Boolean) = launchUI(list) {
        val res = repository.listMyCollect(page)
        Pair(res.data, isMore)
    }

}