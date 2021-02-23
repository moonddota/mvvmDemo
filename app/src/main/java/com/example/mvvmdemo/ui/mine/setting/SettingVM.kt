package com.example.mvvmdemo.ui.mine.setting

import androidx.lifecycle.MutableLiveData
import com.blankj.utilcode.util.SPUtils
import com.example.mvvmdemo.base.BaseViewModel
import com.example.mvvmdemo.constant.C
import com.example.mvvmdemo.network.BaseData
import com.example.mvvmdemo.network.api.RequestService
import com.example.mvvmdemo.ui.mine.MineRepository
import com.example.mvvmdemo.util.CacheUtil

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
        SPUtils.getInstance().remove(C.USER_INFO)
        true
    }


}