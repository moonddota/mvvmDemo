package com.example.mvvmdemo.base

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import com.example.mvvmdemo.util.toast
import kotlinx.coroutines.TimeoutCancellationException
import org.json.JSONException
import java.net.ConnectException
import java.net.UnknownHostException

abstract class BaseViewModelActivity<VM : BaseViewModel, VB : ViewBinding> : BaseActivity<VB>() {

    protected lateinit var viewModel: VM

    override fun onCreate(savedInstanceState: Bundle?) {
        initVM()
        super.onCreate(savedInstanceState)
        startObserve()
    }

    private fun initVM() {
        providerVMClass().let {
            viewModel = ViewModelProvider(this@BaseViewModelActivity).get(it)
            lifecycle.addObserver(viewModel)
        }
    }

    abstract fun providerVMClass(): Class<VM>

    private fun startObserve() {
        //处理一些通用异常，比如网络超时等
        viewModel.run {
            getError().observe(this@BaseViewModelActivity, {
                requestError(it)
            })
        }
    }

    open fun requestError(it: Exception?) {
        //处理一些已知异常
        it?.run {
            when (it) {
                is TimeoutCancellationException -> toast("请求超时")
                is UnknownHostException -> toast("没有网络")
                is JSONException -> toast("解析错误")
                is ConnectException -> toast("连接失败")
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        lifecycle.removeObserver(viewModel)
    }

}