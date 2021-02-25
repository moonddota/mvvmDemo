package com.example.mvvmdemo.ui.splash

import com.example.mvvmdemo.base.BaseViewModelActivity
import com.example.mvvmdemo.databinding.SplashActivityBinding
import com.example.mvvmdemo.util.ARouterUtil

class SplashAc : BaseViewModelActivity<SplashVM, SplashActivityBinding>() {

    override fun providerVMClass(): Class<SplashVM> = SplashVM::class.java

    override fun initView() {
        binding.particleview.startAnim()
        binding.tv.setOnClickListener {
            viewModel.listProjectsTab()
        }
        binding.particleview.setOnParticleAnimListener {
            viewModel.listProjectsTab()
        }
    }

    override fun initData() {
        viewModel.run {
            list.observe(this@SplashAc, {
                ARouterUtil.jumpMain()
                finish()
            })
        }
    }


}