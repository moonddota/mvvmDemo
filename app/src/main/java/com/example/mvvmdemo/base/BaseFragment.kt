package com.example.mvvmdemo.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.dylanc.viewbinding.inflateBindingWithGeneric
import com.example.mvvmdemo.R
import com.example.mvvmdemo.base.loadsir.EmptyCallback
import com.example.mvvmdemo.base.loadsir.LoadingCallback
import com.gyf.immersionbar.BarHide
import com.gyf.immersionbar.ImmersionBar
import com.kingja.loadsir.core.LoadService
import com.kingja.loadsir.core.LoadSir

abstract class BaseFragment<vb : ViewBinding> : Fragment(), INetView {
    private var _binding: vb? = null
    val binding: vb get() = _binding!!

    private var loadService: LoadService<*>? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = inflateBindingWithGeneric(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initImmersionBar()

        initView()
        initData()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    abstract fun initView()
    abstract fun initData()

    protected open fun immersionBar(): Boolean {
        return false
    }

    private fun initImmersionBar() {
        if (immersionBar()) {
            ImmersionBar.with(this)
                .titleBar(R.id.statusBarView, false)
                .statusBarDarkFont(true)
                .keyboardEnable(true)
                .hideBar(BarHide.FLAG_HIDE_NAVIGATION_BAR)
                .init()
        }
    }

    override fun showLoading() {
        if (loadService == null) {
            loadService = LoadSir.getDefault().register(this) { v: View? -> onRetryBtnClick() }
        }
        loadService!!.showCallback(LoadingCallback::class.java)
    }

    override fun showLoading(view: View?) {
        if (loadService == null) {
            loadService = LoadSir.getDefault().register(view) { v: View? -> onRetryBtnClick() }
        }
        loadService!!.showCallback(LoadingCallback::class.java)
    }

    override fun showSuccess() {
        if (loadService == null) {
            loadService = LoadSir.getDefault().register(this) { v: View? -> onRetryBtnClick() }
        }
        loadService!!.showSuccess()

    }

    override fun showEmpty() {
        if (loadService == null) {
            loadService = LoadSir.getDefault().register(this) { v: View? -> onRetryBtnClick() }
        }
        loadService!!.showCallback(EmptyCallback::class.java)
    }

    override fun onRetryBtnClick() {

    }

}