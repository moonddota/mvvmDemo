package com.example.mvvmdemo.network.api

import androidx.lifecycle.LiveData
import com.example.mvvmdemo.base.TreeListRes
import com.example.mvvmdemo.bean.*
import com.example.mvvmdemo.network.BaseData
import com.example.mvvmdemo.network.RetrofitImpl
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

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

    suspend fun listArticle(page: Int): BaseData<ArticleListRes> =
        requestService.listArticle(page)

    suspend fun listArticle(page: Int, id: String): BaseData<ArticleListRes> =
        requestService.listArticle(page, id)

    suspend fun unCollect(id: String): BaseData<Any> =
        requestService.unCollect(id)

    suspend fun collect(id: String): BaseData<Any> =
        requestService.collect(id)

    suspend fun listProjects(page: Int, id: String): BaseData<ArticleListRes> =
        requestService.listProjects(page, id)

    suspend fun listTrees(): BaseData<List<TreeListRes>> =
        requestService.listTrees()

    suspend fun listNavis(): BaseData<List<TreeListRes>> =
        requestService.listNavis()

    suspend fun listArticle(id: String, page: Int): BaseData<ArticleListRes> =
        requestService.listArticle(id, page)

    suspend fun listPublicAuthor(): BaseData<List<PublicAuthorListRes>> =
        requestService.listPublicAuthor()

    suspend fun getIntegral(): BaseData<UserInfo> =
        requestService.getIntegral()

    suspend fun logout(): BaseData<Any>  =
        requestService.logout()
}