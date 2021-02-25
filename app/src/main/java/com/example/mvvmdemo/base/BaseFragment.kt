package com.example.mvvmdemo.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.dylanc.viewbinding.inflateBindingWithGeneric
import com.example.mvvmdemo.R
import com.gyf.immersionbar.BarHide
import com.gyf.immersionbar.ImmersionBar

abstract class BaseFragment<vb : ViewBinding> : Fragment() {
    private val DELAY = 2000L

    private var _binding: vb? = null
    val binding: vb get() = _binding!!

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

}