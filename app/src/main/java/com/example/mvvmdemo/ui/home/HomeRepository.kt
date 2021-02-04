package com.example.mvvmdemo.ui.home

import androidx.lifecycle.MutableLiveData
import com.example.mvvmdemo.bean.BannerRes
import com.example.mvvmdemo.bean.ProjectListRes
import com.example.mvvmdemo.network.BaseData
import com.example.mvvmdemo.network.BaseRepository
import com.example.mvvmdemo.network.api.RequestService

class HomeRepository : BaseRepository() {

    suspend fun getBanner(): BaseData<List<BannerRes>> = request {
        RequestService.instance.getBanner()
    }

}