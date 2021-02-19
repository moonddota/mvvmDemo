package com.example.mvvmdemo.network.api

import androidx.lifecycle.LiveData
import com.example.mvvmdemo.bean.ArticleListRes
import com.example.mvvmdemo.bean.BannerRes
import com.example.mvvmdemo.bean.ProjectListRes
import com.example.mvvmdemo.network.BaseData
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query


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
    suspend fun getBanner(): BaseData<List<BannerRes>>

    /**
     * 获取文章列表
     * @param page 页码，拼接在连接中，从0开始。
     */
    @GET("article/list/{page}/json")
    suspend fun listArticle(@Path("page") page: Int): BaseData<ArticleListRes>


    /**
     * 取消收藏
     */
    @POST("lg/uncollect_originId/{id}/json")
    suspend fun unCollect(@Path("id") id: String): BaseData<Any>

    /**
     * 收藏文章
     */
    @POST("lg/collect/{id}/json")
    suspend fun collect(@Path("id") id: String): BaseData<Any>

    /**
     * 项目列表
     * @param id   分类id
     * @param page 页码，拼接在连接中，从0开始。
     */
    @GET("project/list/{page}/json")
    suspend fun listProjects(@Path("page") page: Int, @Query("cid") id: String): BaseData<ArticleListRes>

}