package com.example.mvvmdemo.ui.mine.setting

import com.example.mvvmdemo.network.BaseData
import com.example.mvvmdemo.network.BaseRepository
import com.example.mvvmdemo.network.api.RequestService

class SettingRepository:BaseRepository() {
    suspend fun logout(): BaseData<Any> = request {
        RequestService.instance.logout()
    }

}