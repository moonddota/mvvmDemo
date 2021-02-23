package com.example.mvvmdemo.ui

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.view.KeyEvent
import android.view.View
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.alibaba.android.arouter.facade.annotation.Route
import com.blankj.utilcode.util.PhoneUtils.call
import com.example.mvvmdemo.base.BaseViewModelActivity
import com.example.mvvmdemo.constant.RouterActivityPath
import com.example.mvvmdemo.databinding.ActivityMainBinding
import com.example.mvvmdemo.ui.home.HomeFg
import com.example.mvvmdemo.ui.mine.MineFg
import com.example.mvvmdemo.ui.project.ProjectFg
import com.example.mvvmdemo.ui.publics.PublicFg
import com.example.mvvmdemo.ui.square.SquareFg
import com.example.mvvmdemo.util.toast
import com.example.mvvmdemo.widget.bubblenavigation.listener.BubbleNavigationChangeListener
import com.permissionx.guolindev.PermissionX


@Route(path = RouterActivityPath.Main.PAGER_MAIN)
class MainActivity : BaseViewModelActivity<MainViewModel, ActivityMainBinding>(),
    BubbleNavigationChangeListener {

    private val mFragments by lazy {
        listOf(
            HomeFg(),
            ProjectFg(),
            SquareFg(),
            PublicFg(),
            MineFg()
        )
    };


    override fun providerVMClass(): Class<MainViewModel> = MainViewModel::class.java

    override fun initView() {
        initPager()
    }

    @SuppressLint("MissingPermission")
    override fun initData() {
        PermissionX.init(this)
            .permissions(
                Manifest.permission.READ_CONTACTS,
                Manifest.permission.CAMERA,
                Manifest.permission.CALL_PHONE,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.INTERNET,
                Manifest.permission.ACCESS_NETWORK_STATE
            )
            .explainReasonBeforeRequest()
            .onExplainRequestReason { scope, deniedList, beforeRequest ->
                if (beforeRequest) {
                    scope.showRequestReasonDialog(deniedList, "为了保证程序正常工作，请您同意以下权限申请", "我已明白")
                } else {
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

    private fun initPager() {
        binding.tableHome.setBadgeValue(0, null)
        binding.tableHome.setBadgeValue(1, null) //invisible badge
        binding.tableHome.setBadgeValue(2, null)
        binding.tableHome.setBadgeValue(3, null)
        binding.tableHome.setBadgeValue(4, null) //empty badge
        binding.tableHome.setNavigationChangeListener(this)

        binding.pager.isUserInputEnabled = false  //关闭滑动
//        binding.pager.offscreenPageLimit  = mFragments.size
        binding.pager.adapter = object : FragmentStateAdapter(this) {
            override fun getItemCount() = mFragments.size

            override fun createFragment(position: Int): Fragment {
                return mFragments[position]
            }
        }
        binding.pager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
            }
        })
    }

    override fun onNavigationChanged(view: View?, position: Int) {
        binding.pager.setCurrentItem(position, true)
    }

    //用户按返回按钮不关闭页面，而是返回到系统桌面。相当于按home键
    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            val home = Intent(Intent.ACTION_MAIN)
            home.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            home.addCategory(Intent.CATEGORY_HOME)
            startActivity(home)
            return true
        }
        return super.onKeyDown(keyCode, event)
    }


}




