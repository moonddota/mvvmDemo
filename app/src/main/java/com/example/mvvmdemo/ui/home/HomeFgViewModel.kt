package com.example.mvvmdemo.ui.home

import androidx.lifecycle.MutableLiveData
import com.example.mvvmdemo.base.BaseViewModel
import com.example.mvvmdemo.bean.BannerRes
import com.example.mvvmdemo.ui.splash.SplashRepository

class HomeFgViewModel:BaseViewModel() {
    private val repository = HomeRepository()


    val bannerList = MutableLiveData<List<BannerRes>>()
    fun getBanner()= launchUI(bannerList) {
        val listRes = repository.getBanner()
        listRes.data?: listOf()
    }

}