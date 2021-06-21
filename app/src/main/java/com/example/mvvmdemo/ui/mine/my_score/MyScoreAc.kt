package com.example.mvvmdemo.ui.mine.my_score

import android.animation.ValueAnimator
import android.view.animation.DecelerateInterpolator
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.alibaba.android.arouter.facade.annotation.Route
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.example.mvvmdemo.Paging3.PagingWrapAdapter
import com.example.mvvmdemo.R
import com.example.mvvmdemo.base.BaseViewModelActivity
import com.example.mvvmdemo.bean.RankBean
import com.example.mvvmdemo.constant.C
import com.example.mvvmdemo.constant.RouterActivityPath
import com.example.mvvmdemo.databinding.MyScoreActivityBinding
import com.example.mvvmdemo.util.ARouterUtil
import com.example.mvvmdemo.util.MMkvHelper
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@Route(path = RouterActivityPath.MyScore.MYSCOREAC)
class MyScoreAc : BaseViewModelActivity<MyScoreVM, MyScoreActivityBinding>() {

    private val mAdapter by lazy {
        val adapterMyScoreList = RvAdapterMyScoreList()
        PagingWrapAdapter<RankBean, BaseViewHolder>(adapterMyScoreList) {
            adapterMyScoreList.setList(it)
        }
    }

    override fun providerVMClass(): Class<MyScoreVM> = MyScoreVM::class.java
    override fun initView() {
        binding.topbar.tvTitle.text = "我的积分"
        binding.topbar.ivLeft.setOnClickListener { finish() }
        with(binding.topbar.ivRight) {
            isVisible = true
            setBackgroundResource(R.mipmap.ic_question)
            setOnClickListener { ARouterUtil.jumpWeb(C.INTERGRAL_URL) }
        }

        binding.aaa.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.aaa.recyclerView.adapter = mAdapter
        binding.aaa.smartRefreshLayout.setOnRefreshListener {
            viewModel.getPagingData()
        }
    }

    override fun initData() {
        startAnim(binding.head.tvScore)
        viewModel.getPagingData()
        viewModel.data.observe(this, {
            lifecycleScope.launch {
                it.collect { pagingData ->
                    mAdapter.submitList(pagingData)
                }
            }
            binding.aaa.smartRefreshLayout.finishRefresh()
            binding.aaa.smartRefreshLayout.finishLoadMore()
        })
    }


    private fun startAnim(textView: TextView) {
        val coinCount: String = MMkvHelper.getInstance().userInfo?.coinCount ?: "0"

        val valueAnimator = ValueAnimator.ofInt(0, coinCount.toInt())
        //播放时长
        valueAnimator.duration = C.DURATION
        valueAnimator.interpolator = DecelerateInterpolator()
        valueAnimator.addUpdateListener { valueAnimator1: ValueAnimator ->
            //获取改变后的值
            val currentValue = valueAnimator1.animatedValue as Int
            textView.text = currentValue.toString() + ""
        }
        valueAnimator.start()
    }
}