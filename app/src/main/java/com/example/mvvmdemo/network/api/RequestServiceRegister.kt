package com.example.mvvmdemo.network.api

import androidx.lifecycle.LiveData
import com.example.mvvmdemo.bean.ArticleListRes
import com.example.mvvmdemo.bean.BannerRes
import com.example.mvvmdemo.bean.ProjectListRes
import com.example.mvvmdemo.network.BaseData
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path


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

    /**
     * 获取文章列表
     *
     * @param page 页码，拼接在连接中，从0开始。
     * @return
     */
    @GET("article/list/{page}/json")
    suspend fun listArticle(@Path("page") page: Int):  BaseData<ArticleListRes>


    /**
     * 取消收藏
     *
     * @param id
     * @return
     */
    @POST("lg/uncollect_originId/{id}/json")
    suspend fun unCollect(@Path("id") id: String): BaseData<Any>

    /**
     * 收藏文章
     *
     * @param id
     * @return
     */
    @POST("lg/collect/{id}/json")
    suspend fun collect(@Path("id") id: String): BaseData<Any>

}