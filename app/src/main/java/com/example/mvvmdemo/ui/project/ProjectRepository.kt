package com.example.mvvmdemo.ui.project

import com.example.mvvmdemo.bean.ArticleListRes
import com.example.mvvmdemo.bean.ProjectListRes
import com.example.mvvmdemo.network.BaseData
import com.example.mvvmdemo.network.BaseRepository
import com.example.mvvmdemo.network.api.RequestService

class ProjectRepository : BaseRepository() {

    suspend fun listProjectsTab(): BaseData<List<ProjectListRes>> = request {
        RequestService.instance.listProjectsTab()
    }

    suspend fun listProjects(page: Int, id: String): BaseData<ArticleListRes> = request {
        RequestService.instance.listProjects(page, id)
    }

    suspend fun unCollect(id: String): BaseData<Any> = request {
        RequestService.instance.unCollect(id)
    }

    suspend fun collect(id: String): BaseData<Any> = request {
        RequestService.instance.collect(id)
    }

}