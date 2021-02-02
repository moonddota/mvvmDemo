package com.example.mvvmdemo.base.loadsir

import com.example.mvvmdemo.R
import com.kingja.loadsir.callback.Callback

class ErrorCallback : Callback() {
    override fun onCreateView(): Int {
        return R.layout.base_layout_error
    }
}