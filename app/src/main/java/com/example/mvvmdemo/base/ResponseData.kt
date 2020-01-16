package com.example.mvvmdemo.base

data class ResponseData<out T>(val code: Int, val message: String, val tdata: T?) {


    fun chakeData(): Boolean {
        if (code == 0) {
            return true
        } else {
            return false
        }
    }


}