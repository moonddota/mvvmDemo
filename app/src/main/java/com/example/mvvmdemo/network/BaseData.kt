package com.example.mvvmdemo.network

class BaseData<T> {
    var errorCode = 0
    var errorMsg: String = ""
    var data: T? = null
}