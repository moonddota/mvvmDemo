package com.example.mvvmdemo.ui.mine

import android.view.View
import android.widget.RelativeLayout
import androidx.core.view.isVisible
import com.example.mvvmdemo.R
import com.example.mvvmdemo.base.BaseViewModelFragment
import com.example.mvvmdemo.bean.UserInfo
import com.example.mvvmdemo.databinding.MineFragmentBinding
import com.example.mvvmdemo.util.ARouterUtil
import com.example.mvvmdemo.util.MMkvHelper
import com.example.mvvmdemo.util.toast
import java.lang.String

class MineFg : BaseViewModelFragment<MineVM, MineFragmentBinding>() {

    private var userInfo: UserInfo? = null

    override fun immersionBar(): Boolean = true

    override fun providerVMClass(): Class<MineVM> = MineVM::class.java

    override fun initView() {
        val layoutParams = binding.llHead.layoutParams as RelativeLayout.LayoutParams
        binding.waveView.setOnWaveAnimationListener { y ->
            layoutParams.setMargins(0, y.toInt(), 0, 0)
            binding.llHead.layoutParams = layoutParams
        }

        binding.ivAvatar.setOnClickListener { ARouterUtil.jumpLogin() }
        binding.ivSet.setOnClickListener { checkLogin { ARouterUtil.jumpSetting() } }
        binding.tvScoreRankList.setOnClickListener { checkLogin { ARouterUtil.jumpScoreRankListAc() } }
        binding.llScore.setOnClickListener { checkLogin { ARouterUtil.jumpMyScoreAc() } }
        binding.llCollect.setOnClickListener { checkLogin { ARouterUtil.jumpMyCollectAc() } }
        binding.llShare.setOnClickListener { checkLogin { ARouterUtil.jumpMyShareAc() } }
        binding.llProjects.setOnClickListener { ARouterUtil.jumpOpenSourceAc() }
        binding.llAbout.setOnClickListener { toast("谢谢使用") }
        binding.llpagin3.setOnClickListener { ARouterUtil.jumpPagin3() }
        binding.llCamera1.setOnClickListener { ARouterUtil.jumpCamera1() }
    }

    override fun initData() {
        viewModel.info.observe(viewLifecycleOwner, {
            binding.tvLevel.isVisible = true
            val userInfo1 = it
            binding.tvId.text = String.format("ID: %s", userInfo1?.userId ?: "")
            binding.tvLevel.text = String.format("lv.%d", userInfo1?.level ?: 0)
            binding.tvMyScore.text = String.format(
                getString(R.string.mine_current_score) + ": %s",
                userInfo1?.coinCount ?: ""
            )
            if (userInfo != null) {
                userInfo1?.username = userInfo!!.username
            }
            MMkvHelper.getInstance().saveUserInfo(userInfo1)
        })
    }

    override fun onResume() {
        super.onResume()
        userInfo = MMkvHelper.getInstance().userInfo
        if (userInfo != null) {
            binding.tvName.text = userInfo?.username ?: ""
            viewModel.getScore()
        } else {
            binding.tvId.text = ""
            binding.tvMyScore.text = ""
            binding.tvName.text = getString(R.string.unlogin)
            binding.tvLevel.visibility = View.GONE
        }
    }

    private fun checkLogin(function: () -> Unit) {
        val userInfo = MMkvHelper.getInstance().userInfo
        if (userInfo == null) {
            toast("清先登录")
        } else {
            function()
        }
    }

}