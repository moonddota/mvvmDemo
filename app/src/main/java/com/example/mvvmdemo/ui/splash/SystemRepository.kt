package com.example.mvvmdemo.ui.splash

import com.example.mvvmdemo.base.TreeListRes
import com.example.mvvmdemo.network.BaseData
import com.example.mvvmdemo.network.BaseRepository
import com.example.mvvmdemo.network.api.RequestService

class SystemRepository :BaseRepository(){

    suspend fun listTrees(): BaseData<List<TreeListRes>> = request {
        RequestService.instance.listTrees()
    }

    suspend fun listNavis(): BaseData<List<TreeListRes>> = request {
        RequestService.instance.listNavis()
    }


}