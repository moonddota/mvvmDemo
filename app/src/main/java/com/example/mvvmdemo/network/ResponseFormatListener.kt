package com.example.mvvmdemo.network

interface ResponseFormatListener<T> {

    fun onSuccess(t: T?)

    fun onFailed(code: Int, msg: String)
}