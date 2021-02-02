package com.example.mvvmdemo.ui.splash

import androidx.lifecycle.Observer
import com.alibaba.android.arouter.launcher.ARouter
import com.blankj.utilcode.util.SPUtils
import com.example.mvvmdemo.base.BaseViewModelActivity
import com.example.mvvmdemo.constant.RouterActivityPath
import com.example.mvvmdemo.constant.SP
import com.example.mvvmdemo.databinding.SplashActivityBinding
import com.example.mvvmdemo.util.ARouterUtil
import com.google.gson.Gson

class SplashAc : BaseViewModelActivity<SplashVM, SplashActivityBinding>() {

    override fun providerVMClass(): Class<SplashVM> = SplashVM::class.java

    override fun initView() {
        binding.particleview.startAnim()
        viewModel.listProjectsTab()
        binding.tv.setOnClickListener {
            viewModel.listProjectsTab()
        }
    }

    override fun initData() {
        viewModel.run {
            list.observe(this@SplashAc, {
                binding.particleview.setOnParticleAnimListener {
                    if (it.isNotEmpty()) {
                        SPUtils.getInstance().put(SP.PROJECT_TABS, Gson().toJson(it))
                        Thread.sleep(1000)
                        ARouterUtil.jumpMain()
                        finish()
                    }
                }
            })
        }
    }


}