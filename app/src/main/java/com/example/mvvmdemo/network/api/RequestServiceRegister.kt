package com.example.mvvmdemo.network.api

import androidx.lifecycle.LiveData
import com.example.mvvmdemo.bean.BannerRes
import com.example.mvvmdemo.bean.ProjectListRes
import com.example.mvvmdemo.network.BaseData
import retrofit2.http.GET


interface RequestServiceRegister {


    /**
     * 项目分类
     */
    @GET("project/tree/json")
    suspend fun listProjectsTab(): BaseData<List<ProjectListRes>>


    /**
     * 获取轮播图
     */
    @GET("banner/json")
    suspend fun getBanner():  BaseData<List<BannerRes>>

}