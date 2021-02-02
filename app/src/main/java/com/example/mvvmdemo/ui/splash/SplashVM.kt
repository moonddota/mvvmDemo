package com.example.mvvmdemo.ui.splash

import androidx.lifecycle.MutableLiveData
import com.example.mvvmdemo.base.BaseViewModel
import com.example.mvvmdemo.bean.ProjectListRes

class SplashVM:BaseViewModel() {

    private val repository = SplashRepository()

    val list = MutableLiveData<List<ProjectListRes>>()
    fun listProjectsTab() = launchUI(list) {
        val listRes = repository.listProjectsTab()
        listRes.data?: listOf()
    }


}