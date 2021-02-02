package com.example.mvvmdemo.ui

import android.Manifest
import android.annotation.SuppressLint
import com.alibaba.android.arouter.facade.annotation.Route
import com.blankj.utilcode.util.PhoneUtils.call
import com.example.mvvmdemo.base.BaseViewModelActivity
import com.example.mvvmdemo.constant.RouterActivityPath
import com.example.mvvmdemo.databinding.ActivityMainBinding
import com.example.mvvmdemo.dialog.MainDialog
import com.example.mvvmdemo.util.toast
import com.permissionx.guolindev.PermissionX


@Route(path = RouterActivityPath.Main.PAGER_MAIN)
class MainActivity : BaseViewModelActivity<MainViewModel, ActivityMainBinding>() {

    private val dialog by lazy { MainDialog() }


    override fun providerVMClass(): Class<MainViewModel> = MainViewModel::class.java

    override fun initView() {
        binding.tv.setOnClickListener {
            dialog.show(supportFragmentManager, "MainActivity")
        }
    }


    @SuppressLint("MissingPermission")
    override fun initData() {

        PermissionX.init(this)
            .permissions(
                Manifest.permission.READ_CONTACTS,
                Manifest.permission.CAMERA,
                Manifest.permission.CALL_PHONE
            )
            .explainReasonBeforeRequest()
            .onExplainRequestReason { scope, deniedList,beforeRequest  ->
                if (beforeRequest) {
                    scope.showRequestReasonDialog(deniedList, "为了保证程序正常工作，请您同意以下权限申请", "我已明白")
                }else{
                    scope.showRequestReasonDialog(
                        deniedList,
                        "核心基础基于这些权限",
                        "OK",
                        "Cancel"
                    )
                }
            }
            .onForwardToSettings { scope, deniedList ->
                scope.showForwardToSettingsDialog(deniedList, "您需要手动在“设置”中允许必要的权限", "OK", "Cancel")
            }
            .request { allGranted, grantedList, deniedList ->
                if (allGranted) {
                    call("13056663582")
                } else {
                    toast("您拒绝了如下权限：$deniedList")
                }
            }
    }


}




