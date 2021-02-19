package com.example.mvvmdemo.ui.splash

import androidx.lifecycle.MutableLiveData
import com.blankj.utilcode.util.SPUtils
import com.example.mvvmdemo.base.BaseViewModel
import com.example.mvvmdemo.bean.ProjectListRes
import com.example.mvvmdemo.constant.SP
import com.google.gson.Gson
import kotlinx.coroutines.delay

class SplashVM : BaseViewModel() {

    private val repository = SplashRepository()

    val list = MutableLiveData<List<ProjectListRes>>()
    fun listProjectsTab() = launchUI(list) {
        val listRes = repository.listProjectsTab()
        if (listRes.data?.isNullOrEmpty() == false) {
            SPUtils.getInstance().put(SP.PROJECT_TABS, Gson().toJson(listRes.data))
        }
        delay(1000)
        listRes.data ?: listOf()
    }


}