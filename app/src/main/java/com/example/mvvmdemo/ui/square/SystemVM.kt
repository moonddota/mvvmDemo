package com.example.mvvmdemo.ui.square

import androidx.lifecycle.MutableLiveData
import com.example.mvvmdemo.base.BaseViewModel
import com.example.mvvmdemo.base.TreeListRes
import com.example.mvvmdemo.ui.splash.SystemRepository

class SystemVM : BaseViewModel() {
    private val repository = SystemRepository()

    val data = MutableLiveData<List<TreeListRes>>()

    fun listTrees() = launchUI(data) {
        val listRes = repository.listTrees()
        listRes.data ?: listOf()
    }

    fun listNavis() = launchUI(data) {
        val listRes = repository.listNavis()
        listRes.data ?: listOf()
    }

}