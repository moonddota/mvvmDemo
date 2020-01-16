package com.example.mvvmdemo.base

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

open class BaseRepository {
    suspend fun <T : Any> request(call: suspend () -> ResponseData<T>): ResponseData<T> {
        return withContext(Dispatchers.IO) { call.invoke() }.apply {
            //这儿可以对返回结果errorCode做一些特殊处理，比如上传参数错误等，可以通过抛出异常的方式实现
            //例：当上传参数错误时，后台返回errorCode 为 1001，下面代码实现,再到baseActivity通过观察error来处理
            when (code) {
                1001 -> throw ParameterException()
                0 -> Log.e("请求状态值:$code", "请求成功" );
            }
        }
    }

    class ParameterException(msg: String = "Parameter error") : Exception(msg)
}