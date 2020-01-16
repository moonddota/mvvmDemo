package com.example.mvvmdemo.http

import com.example.mvvmdemo.base.ResponseData
import com.example.mvvmdemo.data.FindDeviceStatusNew
import com.example.mvvmdemo.data.loginData
import retrofit2.http.*

interface RequestService {

    @FormUrlEncoded
    @POST("user/login")
    suspend fun login(@Field("username") userName: String, @Field("password") passWord: String): ResponseData<loginData>

    @GET("device/find_device_status/{deviceNo}")
    suspend fun findTerminalStatusNew(@Path("deviceNo") deviceNo: String): ResponseData<FindDeviceStatusNew>

}