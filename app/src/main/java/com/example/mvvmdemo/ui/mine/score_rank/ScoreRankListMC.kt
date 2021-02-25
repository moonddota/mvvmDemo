package com.example.mvvmdemo.ui.mine.score_rank

import androidx.lifecycle.MutableLiveData
import com.example.mvvmdemo.base.BaseViewModel
import com.example.mvvmdemo.bean.RankListRes
import com.example.mvvmdemo.ui.mine.setting.SettingRepository

class ScoreRankListMC : BaseViewModel() {
    private val repository = ScoreRankListRepository()

    val list = MutableLiveData<Pair<RankListRes?,Boolean>>()
    fun getList(page: Int,isMore:Boolean) = launchUI(list) {
        val res = repository.listScoreRank(page)
        Pair(res.data,isMore)
    }

}