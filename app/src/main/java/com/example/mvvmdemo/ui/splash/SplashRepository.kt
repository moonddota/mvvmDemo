package com.example.mvvmdemo.ui.splash

import com.example.mvvmdemo.bean.ProjectListRes
import com.example.mvvmdemo.network.BaseData
import com.example.mvvmdemo.network.BaseRepository
import com.example.mvvmdemo.network.api.RequestService

class SplashRepository : BaseRepository() {

    suspend fun listProjectsTab(): BaseData<List<ProjectListRes>> = request {
        RequestService.instance.listProjectsTab()
    }

}