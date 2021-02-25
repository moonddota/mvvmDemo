package com.example.mvvmdemo.ui.mine

import com.example.mvvmdemo.bean.UserInfo
import com.example.mvvmdemo.network.BaseData
import com.example.mvvmdemo.network.BaseRepository
import com.example.mvvmdemo.network.api.RequestService

class MineRepository:BaseRepository() {

    suspend fun getIntegral(): BaseData<UserInfo> = request {
        RequestService.instance.getIntegral()
    }



}