package com.example.mvvmdemo.base

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

abstract class BaseRetrofitClient {

    /**
     * 获取对应的service
     * @param service service class
     * @param baseUrl basUrl
     * @return service
     */
    fun <S> getService(service: Class<S>,baseUrl: String): S {
        return Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
//                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .build()
                .create(service)
    }
}