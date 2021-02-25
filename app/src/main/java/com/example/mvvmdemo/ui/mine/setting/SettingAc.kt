package com.example.mvvmdemo.ui.mine.setting

import com.alibaba.android.arouter.facade.annotation.Route
import com.blankj.utilcode.util.AppUtils
import com.example.mvvmdemo.R
import com.example.mvvmdemo.base.BaseViewModelActivity
import com.example.mvvmdemo.constant.RouterActivityPath
import com.example.mvvmdemo.databinding.SettingActivityBinding
import com.example.mvvmdemo.dialog.TwoButtonDialog
import com.example.mvvmdemo.util.ARouterUtil
import com.example.mvvmdemo.util.LanguageUtil
import com.example.mvvmdemo.util.toast
import java.util.*

@Route(path = RouterActivityPath.Setting.SETTING)
class SettingAc : BaseViewModelActivity<SettingVM, SettingActivityBinding>() {

    override fun providerVMClass(): Class<SettingVM> = SettingVM::class.java

    override fun initView() {
        binding.topbar.tvTitle.text = getString(R.string.mine_set)
        binding.topbar.ivLeft.setOnClickListener { finish() }
        binding.tvVersion.text = AppUtils.getAppVersionName()

        val currentLanguage: Locale = LanguageUtil.getCurrentLanguage()
        binding.tvLanguage.text =
            String.format("%s-%s", currentLanguage.language, currentLanguage.country)

        binding.llCache.setOnClickListener {
            clearUpDialog.show(supportFragmentManager,"clearUpDialog")
        }
        binding.llVersion.setOnClickListener {
            PopUtil.show(getString(R.string.version_tip))
        }
        binding.llDesc.setOnClickListener {
            tipDialog.show(supportFragmentManager,"tipDialog")
        }
        binding.llAbout.setOnClickListener {
            ARouterUtil.jumpAboutUs( )
        }
        binding.tvLogOut.setOnClickListener {
            logOutDialog.show(supportFragmentManager,"logOutDialog")
        }
        binding.llLanguage.setOnClickListener {
            toast("待完成")
            return@setOnClickListener
//            todo
//            ARouterUtil.jumpLanguageSet()
        }
    }

    override fun initData() {
        viewModel.getCache()
        viewModel.cacheData.observe(this, { binding.tvCache.text = it })
        viewModel.isLoging.observe(this, { finish() })
    }

    private val clearUpDialog by lazy {
        TwoButtonDialog(
            "确定要清除缓存?",
            getString(R.string.cancel),
            getString(R.string.sure),
            object :TwoButtonDialog.Confirm{
                override fun onConfirm(dialog: TwoButtonDialog) {
                    viewModel.clearCache()
                    dialog.dismiss()
                }
            }
        )
    }

    private val tipDialog by lazy {
        TwoButtonDialog(
            "本App中所有的Api均由WanAndroid官网提供，可作为学习参考，禁止作为商业用途",
            getString(R.string.cancel),
            getString(R.string.sure),
            object :TwoButtonDialog.Confirm{
                override fun onConfirm(dialog: TwoButtonDialog) {
                   dialog.dismiss()
                }
            }
        )
    }

    private val logOutDialog by lazy {
        TwoButtonDialog(
            getString(R.string.sure_logout),
            getString(R.string.cancel),
            getString(R.string.sure),
            object :TwoButtonDialog.Confirm{
                override fun onConfirm(dialog: TwoButtonDialog) {
                    dialog.dismiss()
                    viewModel.logout()
                }
            }
        )
    }


}