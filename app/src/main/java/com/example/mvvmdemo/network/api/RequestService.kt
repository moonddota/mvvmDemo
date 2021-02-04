package com.example.mvvmdemo.network.api

import com.example.mvvmdemo.bean.BannerRes
import com.example.mvvmdemo.bean.ProjectListRes
import com.example.mvvmdemo.network.BaseData
import com.example.mvvmdemo.network.RetrofitImpl

class RequestService {


    private var requestService: RequestServiceRegister =
        RetrofitImpl.instance.getRetrofit().create(
            RequestServiceRegister::class.java
        )

    companion object {
        val instance: RequestService by
        lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) { RequestService() }
    }


    suspend fun listProjectsTab(): BaseData<List<ProjectListRes>> =
        requestService.listProjectsTab()

    suspend fun getBanner(): BaseData<List<BannerRes>> =
        requestService.getBanner()

}