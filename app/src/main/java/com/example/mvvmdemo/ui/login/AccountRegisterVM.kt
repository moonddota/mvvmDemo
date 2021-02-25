package com.example.mvvmdemo.ui.login

import androidx.lifecycle.MutableLiveData
import com.blankj.utilcode.util.SPUtils
import com.example.mvvmdemo.base.BaseViewModel
import com.example.mvvmdemo.constant.C
import com.google.gson.Gson

class AccountRegisterVM : BaseViewModel() {

    private val repository = LoginRepository()

    val data = MutableLiveData<Boolean>()
    fun register(
        username: String,
        password: String,
        repassword: String
    ) = launchUI(data, {
        data.postValue(false)
    }) {
        val res = repository.register(username, password, repassword)
        SPUtils.getInstance().put(C.USER_INFO, Gson().toJson(res.data)?:"")
        true
    }

}