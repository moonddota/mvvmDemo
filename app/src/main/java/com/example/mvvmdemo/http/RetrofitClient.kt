package com.example.mvvmdemo.http

import com.example.mvvmdemo.BuildConfig
import com.example.mvvmdemo.util.SharePreUtil
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitClient {

    private val BASE_URL = BuildConfig.HOST
    //    private val BASE_URL = "https://wanandroid.com/"
    private val TIMEOUT_CONNECTION = 5
    private val TIMEOUT_READ = 10
    private val TIMEOUT_WRITE = 10

    val reqApi by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(initClient())
            .build()
        return@lazy retrofit.create(RequestService::class.java)
    }

    private fun initClient(): OkHttpClient {
        val builder = OkHttpClient().newBuilder()
            .connectTimeout(TIMEOUT_CONNECTION.toLong(), TimeUnit.SECONDS) //连接超时
            .readTimeout(TIMEOUT_READ.toLong(), TimeUnit.SECONDS) //读取超时
            .writeTimeout(TIMEOUT_WRITE.toLong(), TimeUnit.SECONDS) //写入超时
            .addInterceptor(getHeaderInterceptor())//设置Header
            .addInterceptor(getInterceptor())//设置拦截器
            .retryOnConnectionFailure(true)//设置重连
        return builder.build()
    }

    private fun getHeaderInterceptor(): Interceptor {
        return Interceptor { chain ->
            val userId = SharePreUtil.get("userId", 1)
            val sessionId = SharePreUtil.get("sessionId", "")
            val original = chain.request()
            val requestBuilder = original.newBuilder()
                .header("userId", userId.toString())//添加Token
                .addHeader("sessionId", sessionId.toString())

            val request = requestBuilder.build()
            chain.proceed(request)
        }
    }

    private fun getInterceptor(): Interceptor {
        val interceptor = HttpLoggingInterceptor()
        //显示日志
        if (BuildConfig.DEBUG) {
            interceptor.level = HttpLoggingInterceptor.Level.BODY
        }
        return interceptor
    }


}