package com.example.mvvmdemo.ui.splash

import androidx.lifecycle.MutableLiveData
import com.example.mvvmdemo.base.BaseViewModel
import com.example.mvvmdemo.bean.ProjectListRes
import com.example.mvvmdemo.util.MMkvHelper
import kotlinx.coroutines.delay

class SplashVM : BaseViewModel() {

    private val repository = SplashRepository()

    val list = MutableLiveData<List<ProjectListRes>>()
    fun listProjectsTab() = launchUI(list) {
        val listRes = repository.listProjectsTab()
        if (listRes.data?.isNullOrEmpty() == false) {
            MMkvHelper.getInstance().saveProjectTabs(listRes.data)
        }
        delay(1000)
        listRes.data ?: listOf()
    }


}