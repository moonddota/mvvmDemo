package com.example.mvvmdemo.ui.mine

import android.view.View
import android.widget.RelativeLayout
import com.blankj.utilcode.util.SPUtils
import com.example.mvvmdemo.R
import com.example.mvvmdemo.base.BaseViewModelFragment
import com.example.mvvmdemo.bean.UserInfo
import com.example.mvvmdemo.constant.C
import com.example.mvvmdemo.databinding.MineFragmentBinding
import com.example.mvvmdemo.util.ARouterUtil
import com.google.gson.Gson
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

        binding.ivSet.setOnClickListener { ARouterUtil.jumpSetting()}
//        binding.tvScoreRankList.setOnClickListener { ScoreRankListAc.launch(context) }
//        binding.llScore.setOnClickListener { MyScoreAc.launch(context) }
//        binding.llCollect.setOnClickListener { MyCollectAc.launch(context) }
//        binding.llShare.setOnClickListener { MyShareAc.launch(context) }
//        binding.llProjects.setOnClickListener { OpenSourceAc.launch(context) }
//        binding.llAbout.setOnClickListener { AboutAuthorAc.launch(context) }

    }

    override fun initData() {
        viewModel.info.observe(viewLifecycleOwner, {
            binding.tvLevel.visibility = View.VISIBLE
            var userInfo1 = it
            binding.tvId.text = String.format("ID: %s", userInfo1?.userId ?: "")
            binding.tvLevel.text = String.format("lv.%d", userInfo1?.level ?: 0)
            binding.tvMyScore.text = String.format(
                getString(R.string.mine_current_score) + ": %s",
                userInfo1?.coinCount ?: ""
            )
            if (userInfo != null) {
                userInfo1?.username = userInfo!!.username
            }
            SPUtils.getInstance().put(C.USER_INFO, Gson().toJson(userInfo1))
        })
    }

    override fun onResume() {
        super.onResume()
        val ss = SPUtils.getInstance().getString(C.USER_INFO, "")
        userInfo = Gson().fromJson(ss, UserInfo::class.java)
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

}