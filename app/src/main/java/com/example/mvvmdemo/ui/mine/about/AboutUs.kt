package com.example.mvvmdemo.ui.mine.about

import com.alibaba.android.arouter.facade.annotation.Route
import com.example.mvvmdemo.base.BaseActivity
import com.example.mvvmdemo.constant.C
import com.example.mvvmdemo.constant.RouterActivityPath
import com.example.mvvmdemo.databinding.AboutUsActivityBinding
import com.example.mvvmdemo.util.ARouterUtil

@Route(path = RouterActivityPath.AboutUs.ABOUTUS)
class AboutUs : BaseActivity<AboutUsActivityBinding>() {
    override fun initView() {

        binding.topbar.tvTitle.text = "我的模块"
        binding.topbar.ivLeft.setOnClickListener { finish() }

        binding.llOfficial.setOnClickListener { ARouterUtil.jumpWeb(C.WAN_ANDROID) }
        binding.llNetContent.setOnClickListener { ARouterUtil.jumpWeb(C.URL_ABOUT) }
        binding.llLibAddr.setOnClickListener { ARouterUtil.jumpWeb(C.SOURCE_URL) }
    }

    override fun initData() {

    }


}