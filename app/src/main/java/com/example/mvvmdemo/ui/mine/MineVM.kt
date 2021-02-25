package com.example.mvvmdemo.ui.mine

import androidx.lifecycle.MutableLiveData
import com.example.mvvmdemo.base.BaseViewModel
import com.example.mvvmdemo.bean.UserInfo

class MineVM : BaseViewModel() {

    private val repository = MineRepository()

    val info = MutableLiveData<UserInfo?>()
    fun getScore() = launchUI(info) {
        val res = repository.getIntegral()
        res.data
    }




}