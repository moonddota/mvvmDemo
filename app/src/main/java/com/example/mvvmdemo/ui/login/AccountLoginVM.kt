package com.example.mvvmdemo.ui.login

import androidx.lifecycle.MutableLiveData
import com.example.mvvmdemo.base.BaseViewModel
import com.example.mvvmdemo.util.MMkvHelper

class AccountLoginVM : BaseViewModel() {

    private val repository = LoginRepository()

    val data = MutableLiveData<Boolean>()
    fun login(username: String, password: String) = launchUI(data, {
        data.postValue(false)
    }) {
        val res = repository.login(username, password)
        MMkvHelper.getInstance().saveUserInfo(res.data)
        true
    }


}