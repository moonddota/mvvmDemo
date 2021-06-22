package com.example.mvvmdemo.ui.mine.my_score

import androidx.lifecycle.MutableLiveData
import com.example.mvvmdemo.base.BaseViewModel
import com.example.mvvmdemo.bean.RankListRes

class MyScoreVM : BaseViewModel() {

    private val repository = MyScoreRepository()

    val data = MutableLiveData<Pair<RankListRes?, Boolean>>()
    fun getList(page: Int, isMore: Boolean) = launchUI(data) {
        val res = repository.listIntegral(page)
        Pair(res.data, isMore)
    }

}