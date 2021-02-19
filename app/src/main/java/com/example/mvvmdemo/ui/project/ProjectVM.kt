package com.example.mvvmdemo.ui.project

import androidx.lifecycle.MutableLiveData
import com.blankj.utilcode.util.SPUtils
import com.example.mvvmdemo.base.BaseViewModel
import com.example.mvvmdemo.bean.ArticleListRes
import com.example.mvvmdemo.bean.ProjectListRes
import com.example.mvvmdemo.constant.SP
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class ProjectVM : BaseViewModel() {
    private val repository = ProjectRepository()

    val data = MutableLiveData<List<ProjectListRes>>()
    fun getData() = launchUI(data) {
        var projectTabs: List<ProjectListRes> =
            Gson().fromJson(
                SPUtils.getInstance().getString(SP.PROJECT_TABS, ""),
                object : TypeToken<List<ProjectListRes>>() {}.type
            )
        if (projectTabs.isNullOrEmpty()) {
            val listRes = repository.listProjectsTab()
            if (listRes.data?.isNullOrEmpty() == false) {
                SPUtils.getInstance().put(SP.PROJECT_TABS, Gson().toJson(listRes))
            }
            projectTabs = listRes.data ?: listOf()
        }
        projectTabs
    }

    val list = MutableLiveData<Pair<ArticleListRes?,Boolean>>()
    fun getList(page: Int, id: String,isLoadMore:Boolean) = launchUI(list) {
        val listRes = repository.listProjects(page,id)
        Pair(listRes.data, isLoadMore)
    }


    fun unCollect(id: String) = launchUI {
        repository.unCollect(id)
    }

    fun collect(id: String) = launchUI {
        repository.collect(id)
    }

}