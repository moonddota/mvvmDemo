package com.example.mvvmdemo.network.api

import androidx.lifecycle.LiveData
import com.example.mvvmdemo.bean.ArticleListRes
import com.example.mvvmdemo.bean.BannerRes
import com.example.mvvmdemo.bean.ProjectListRes
import com.example.mvvmdemo.network.BaseData
import com.example.mvvmdemo.network.RetrofitImpl
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

    suspend fun unCollect(id: String): BaseData<Any> =
        requestService.unCollect(id)

    suspend fun collect(id: String): BaseData<Any> =
        requestService.collect(id)

    suspend fun listProjects(page: Int, id: String): BaseData<ArticleListRes> =
        requestService.listProjects(page, id)


}