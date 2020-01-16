package com.example.mvvmdemo.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.mvvmdemo.base.BaseViewModel
import com.example.mvvmdemo.base.ResponseData
import com.example.mvvmdemo.data.loginData

class LoginViewModel : BaseViewModel() {

    private var data: MutableLiveData<ResponseData<loginData>> = MutableLiveData()

    private val repository = LoginRepository()

    fun getLogin(): LiveData<ResponseData<loginData>> {

        return data
    }

    fun loginDatas(name: String, pwd: String) = launchUI {
        val result = repository.loginDatas(name, pwd)
        data.postValue(result)
    }
}