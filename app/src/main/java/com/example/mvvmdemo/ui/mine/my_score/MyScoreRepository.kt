package com.example.mvvmdemo.ui.mine.my_score

import com.example.mvvmdemo.bean.RankListRes
import com.example.mvvmdemo.network.BaseData
import com.example.mvvmdemo.network.BaseRepository
import com.example.mvvmdemo.network.api.RequestService

class MyScoreRepository:BaseRepository() {

    suspend fun listIntegral(page: Int): BaseData<RankListRes> = request {
        RequestService.instance.listIntegral(page)
    }


}