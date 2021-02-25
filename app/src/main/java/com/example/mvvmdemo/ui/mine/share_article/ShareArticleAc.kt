package com.example.mvvmdemo.ui.mine.share_article

import android.text.TextUtils
import com.alibaba.android.arouter.facade.annotation.Route
import com.example.mvvmdemo.base.BaseViewModelActivity
import com.example.mvvmdemo.constant.RouterActivityPath
import com.example.mvvmdemo.databinding.ShareArticleActivityBinding
import com.example.mvvmdemo.ui.mine.setting.PopUtil

@Route(path = RouterActivityPath.ShareArticle.ShareArticleAc)
class ShareArticleAc : BaseViewModelActivity<ShareArticleVM, ShareArticleActivityBinding>() {



    override fun providerVMClass(): Class<ShareArticleVM> = ShareArticleVM::class.java

    override fun initView() {
        binding.topbar.tvTitle.text  =  "分享文章"
        binding.topbar.ivLeft.setOnClickListener { finish()}

        binding.tvShare.setOnClickListener {
            val title: String = binding.etTitle.text.toString().trim()
            if (TextUtils.isEmpty(title)) {
                PopUtil.show("请输入标题")
                return@setOnClickListener
            }
            val link: String = binding.etLink.text.toString().trim { it <= ' ' }
            if (TextUtils.isEmpty(link)) {
                PopUtil.show("请输入链接")
            }
            viewModel.shareArticle(title,link)
        }

    }

    override fun initData() {
         viewModel.data.observe(this,{
             PopUtil.show("分享成功") { finish() }
         })
    }


}