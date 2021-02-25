package com.example.mvvmdemo.ui.mine.score_rank

import com.example.mvvmdemo.bean.RankListRes
import com.example.mvvmdemo.network.BaseData
import com.example.mvvmdemo.network.BaseRepository
import com.example.mvvmdemo.network.api.RequestService

class ScoreRankListRepository : BaseRepository() {

    suspend fun listScoreRank(page: Int): BaseData<RankListRes> = request {
        RequestService.instance.listScoreRank(page)
    }

}