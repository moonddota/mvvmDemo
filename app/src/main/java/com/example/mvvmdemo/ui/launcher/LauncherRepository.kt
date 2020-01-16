package com.example.mvvmdemo.ui.launcher

import com.example.mvvmdemo.base.BaseRepository
import com.example.mvvmdemo.base.ResponseData
import com.example.mvvmdemo.data.FindDeviceStatusNew
import com.example.mvvmdemo.data.loginData
import com.example.mvvmdemo.http.RetrofitClient

class LauncherRepository : BaseRepository() {

    suspend fun launcherData(deviceNo :String): ResponseData<FindDeviceStatusNew> = request {
        RetrofitClient.reqApi.findTerminalStatusNew(deviceNo)
    }



}