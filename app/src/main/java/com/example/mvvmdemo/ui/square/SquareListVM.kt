package com.example.mvvmdemo.ui.square

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.blankj.utilcode.util.LogUtils
import com.example.mvvmdemo.base.BaseViewModel
import com.example.mvvmdemo.bean.ArticleListRes
import com.example.mvvmdemo.ui.project.ProjectRepository

class SquareListVM : BaseViewModel() {
    private val repository = SquareListRepository()

    fun unCollect(id: String) = launchUI {
        repository.unCollect(id)
    }

    fun collect(id: String) = launchUI {
        repository.collect(id)
    }

    val dataList = MutableLiveData<Pair<ArticleListRes?, Boolean>>()
    fun getList(page: Int, id: String, isLoadMore: Boolean) = launchUI(dataList,
        {
            LogUtils.e(it)
        }) {
        val listRes = repository.listArticle(page, id)
        Pair(listRes.data, isLoadMore)
    }

}