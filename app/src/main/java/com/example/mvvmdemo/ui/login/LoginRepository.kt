package com.example.mvvmdemo.ui.login

import com.example.mvvmdemo.base.BaseRepository
import com.example.mvvmdemo.base.ResponseData
import com.example.mvvmdemo.data.loginData
import com.example.mvvmdemo.http.RetrofitClient

class LoginRepository : BaseRepository() {

    suspend fun loginDatas(userName: String, passWord: String): ResponseData<loginData> = request {
        RetrofitClient.reqApi.login(userName, passWord)
    }

}