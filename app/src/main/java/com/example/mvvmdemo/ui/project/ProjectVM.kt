package com.example.mvvmdemo.ui.project

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.blankj.utilcode.util.SPUtils
import com.example.mvvmdemo.base.BaseViewModel
import com.example.mvvmdemo.bean.ArticleBean
import com.example.mvvmdemo.bean.ArticleListRes
import com.example.mvvmdemo.bean.ProjectListRes
import com.example.mvvmdemo.constant.C
import com.example.mvvmdemo.constant.SP
import com.example.mvvmdemo.network.api.RequestService
import com.example.mvvmdemo.ui.home.ArticlePagingSource
import com.example.mvvmdemo.util.MMkvHelper
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.flow.Flow

class ProjectVM : BaseViewModel() {
    private val repository = ProjectRepository()

    val data = MutableLiveData<List<ProjectListRes>>()
    fun getData() = launchUI(data) {
        var projectTabs: List<ProjectListRes> =
            MMkvHelper.getInstance().getProjectTabs(ProjectListRes::class.java)
        if (projectTabs.isNullOrEmpty()) {
            val listRes = repository.listProjectsTab()
            if (listRes.data?.isNullOrEmpty() == false) {
                MMkvHelper.getInstance().saveProjectTabs(listRes.data)
            }
            projectTabs = listRes.data ?: listOf()
        }
        projectTabs
    }

    val list = MutableLiveData<Flow<PagingData<ArticleBean>>>()
    fun getList(id: String,) = launchUI(list) {
        Pager(
            config = PagingConfig(pageSize = 10),
            pagingSourceFactory = { ProjectsPagingSource(RequestService.instance,id) }
        ).flow.cachedIn(viewModelScope)
    }


    fun unCollect(id: String) = launchUI {
        repository.unCollect(id)
    }

    fun collect(id: String) = launchUI {
        repository.collect(id)
    }

}