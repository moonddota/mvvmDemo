package com.example.mvvmdemo.base

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import com.example.mvvmdemo.util.toast
import kotlinx.coroutines.TimeoutCancellationException
import org.json.JSONException
import java.net.ConnectException
import java.net.UnknownHostException

abstract class BaseViewModelFragment<VM : BaseViewModel, VB : ViewBinding> : BaseFragment<VB>() {

    protected lateinit var viewModel: VM

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initVM()
        startObserve()
        super.onViewCreated(view, savedInstanceState)
    }

    private fun initVM() {
        providerVMClass().let {
            viewModel = ViewModelProvider(this@BaseViewModelFragment).get(it)
            lifecycle.addObserver(viewModel)
        }
    }

    abstract fun providerVMClass(): Class<VM>

    open fun startObserve() {
        //处理一些通用异常，比如网络超时等
        viewModel.run {
            getError().observe(viewLifecycleOwner, Observer {
                requestError(it)
            })
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (this::viewModel.isInitialized)
            lifecycle.removeObserver(viewModel)
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

}