package com.example.mvvmdemo.ui.login

import com.example.mvvmdemo.bean.UserInfo
import com.example.mvvmdemo.network.BaseData
import com.example.mvvmdemo.network.BaseRepository
import com.example.mvvmdemo.network.api.RequestService

class LoginRepository:BaseRepository() {

   suspend fun login(username: String, password: String): BaseData<UserInfo> = request {
        RequestService.instance.login(username,password)
    }


    suspend fun register(
        username: String,
        password: String,
        repassword: String
    ): BaseData<UserInfo> = request {
        RequestService.instance.register(username,password,repassword)
    }

}