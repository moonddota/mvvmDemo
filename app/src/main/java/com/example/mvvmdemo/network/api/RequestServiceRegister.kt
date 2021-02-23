package com.example.mvvmdemo.network.api

import androidx.lifecycle.LiveData
import com.example.mvvmdemo.base.TreeListRes
import com.example.mvvmdemo.bean.*
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
     * 知识体系下的文章
     * @param page
     * @param id
     */
    @GET("article/list/{page}/json")
    suspend fun listArticle(
        @Path("page") page: Int,
        @Query("cid") id: String?
    ): BaseData<ArticleListRes>

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
    suspend fun listProjects(
        @Path("page") page: Int,
        @Query("cid") id: String
    ): BaseData<ArticleListRes>

    /**
     * 获取轮播图
     */
    @GET("tree/json")
    suspend fun listTrees(): BaseData<List<TreeListRes>>

    /**
     * 获取轮播图
     */
    @GET("navi/json")
    suspend fun listNavis(): BaseData<List<TreeListRes>>

    /**
     * 公众号文章列表
     * @param id   公众号id
     * @param page 页码，拼接在连接中，从0开始。
     */
    @GET("wxarticle/list/{id}/{page}/json")
    suspend fun listArticle(
        @Path("id") id: String,
        @Path("page") page: Int
    ): BaseData<ArticleListRes>

    /**
     * 公众号作业列表
     */
    @GET("wxarticle/chapters/json")
    suspend fun listPublicAuthor(): BaseData<List<PublicAuthorListRes>>

    //获取个人积分
    @GET("lg/coin/userinfo/json")
    suspend fun getIntegral():  BaseData<UserInfo>

    @GET("user/logout/json")
    suspend fun logout(): BaseData<Any>
}