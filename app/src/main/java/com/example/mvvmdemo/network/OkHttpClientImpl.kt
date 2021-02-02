package com.example.mvvmdemo.network

import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit

class OkHttpClientImpl {

    private var okHttpClientBuilder: OkHttpClient.Builder = OkHttpClient.Builder()
        .addInterceptor(HttpLogInterceptor())
        .addInterceptor(RequestHeaderInterceptor())
        .addInterceptor(ResponseErrorInterceptor())
        .retryOnConnectionFailure(true)
        .connectTimeout(20, TimeUnit.SECONDS)
        .readTimeout(20, TimeUnit.SECONDS)
        .writeTimeout(20, TimeUnit.SECONDS)


    private fun getOkHttpClient(): OkHttpClient {
        return okHttpClientBuilder.build()
    }

    companion object {

        fun generate(): OkHttpClient {
            return with(OkHttpClientImpl()) {
                getOkHttpClient()
            }
        }
    }

}
