package com.example.mvvmdemo.network


import android.content.ActivityNotFoundException
import com.example.mvvmdemo.util.toast

open class BaseRepository {
    suspend fun <T : Any> request(call: suspend () -> BaseData<T>): BaseData<T> {
        return call.invoke().apply {
            //这儿可以对返回结果errorCode做一些特殊处理，比如上传参数错误等，可以通过抛出异常的方式实现
            //例：当上传参数错误时，后台返回errorCode 为 1001，下面代码实现,再到baseActivity通过观察error来处理
            when (errorCode) {
//                1001 -> throw ParameterException()
                0 -> {

                }
                else -> {
                    toast(errorMsg)
                    throw NetWorkException(errorMsg)
                }
            }
        }
    }


    class ParameterException(msg: String = "Parameter error") : Exception(msg)
    class NetWorkException(msg: String) : Exception(msg)
}