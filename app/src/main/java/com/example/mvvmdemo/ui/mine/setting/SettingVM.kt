package com.example.mvvmdemo.ui.mine.setting

import androidx.lifecycle.MutableLiveData
import com.example.mvvmdemo.base.BaseViewModel
import com.example.mvvmdemo.util.CacheUtil
import com.example.mvvmdemo.util.MMkvHelper

class SettingVM : BaseViewModel() {

    private val repository = SettingRepository()

    var cacheData = MutableLiveData<String>()
    fun getCache() = launchUI(cacheData) {
        CacheUtil.getTotalCacheSize()
    }

    fun clearCache()= launchUI(cacheData) {
        CacheUtil.clearAllCache()
        CacheUtil.getTotalCacheSize()
    }

    val isLoging = MutableLiveData<Boolean>()
    fun logout() = launchUI(isLoging) {
        repository.logout()
        MMkvHelper.getInstance().logout()
        true
    }


}