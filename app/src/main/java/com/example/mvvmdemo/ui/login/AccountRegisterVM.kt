package com.example.mvvmdemo.ui.login

import androidx.lifecycle.MutableLiveData
import com.example.mvvmdemo.base.BaseViewModel
import com.example.mvvmdemo.constant.C
import com.example.mvvmdemo.util.MMkvHelper
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
        MMkvHelper.getInstance().saveUserInfo(res.data)
        true
    }

}