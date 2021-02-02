package com.example.mvvmdemo.base

import android.view.View

interface INetView {

    fun showLoading()

    fun showLoading(view: View?)

    fun showSuccess()

    fun showEmpty()

    fun onRetryBtnClick()
}