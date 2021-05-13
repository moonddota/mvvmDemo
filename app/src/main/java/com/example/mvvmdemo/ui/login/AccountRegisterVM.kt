package com.example.mvvmdemo.ui.login

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.mvvmdemo.base.BaseViewModel
import com.example.mvvmdemo.constant.C
import com.example.mvvmdemo.util.MMkvHelper
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

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



    fun aaa() {
        GlobalScope.launch(Dispatchers.Main) {
            count().flowOn(Dispatchers.Unconfined) // 指定数据流产生运行线程
                .map {
                    Log.d("Coroutine", "map on ${Thread.currentThread().name}")
                    if (it > 15) {
                        throw NumberFormatException()
                    }
                    "I am $it"
                }.flowOn(Dispatchers.IO)           // 指定map中间action运行线程
                .catch { ex ->
                    Log.d("Coroutine", "catch on ${Thread.currentThread().name}")
                    emit("error")
                }.collect {
                    Log.d("Coroutine", "collect on ${Thread.currentThread().name}")
                }
        }
    }

    private fun count(): Flow<Int> = flow {
        var x = 0
        while (true) {
            if (x > 20) {
                break
            }
            delay(500)
            Log.d("Coroutine", "emit on ${Thread.currentThread().name}")
            emit(x)
            x = x.plus(1)
        }
    }


}