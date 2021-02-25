package com.example.mvvmdemo.ui.mine.score_rank

import android.text.TextUtils
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.alibaba.android.arouter.facade.annotation.Route
import com.blankj.utilcode.util.SPUtils
import com.example.mvvmdemo.R
import com.example.mvvmdemo.base.BaseViewModelActivity
import com.example.mvvmdemo.bean.UserInfo
import com.example.mvvmdemo.constant.C
import com.example.mvvmdemo.constant.RouterActivityPath
import com.example.mvvmdemo.databinding.ScoreRankListActivityBinding
import com.google.gson.Gson
import com.scwang.smart.refresh.layout.api.RefreshLayout
import com.scwang.smart.refresh.layout.listener.OnRefreshLoadMoreListener

@Route(path = RouterActivityPath.ScoreRankList.SCORERANKLISTAC)
class ScoreRankListAc : BaseViewModelActivity<ScoreRankListMC, ScoreRankListActivityBinding>(),
    OnRefreshLoadMoreListener {
    private var page = 1
    private val mAdapter by lazy { RvAdapterScoreRankList() }

    override fun providerVMClass(): Class<ScoreRankListMC> = ScoreRankListMC::class.java

    override fun initView() {
        binding.topbar.tvTitle.text = getString(R.string.mine_scoreboard)
        binding.topbar.ivLeft.setOnClickListener { finish() }
        mAdapter.setOnItemClickListener { adapter, view, position -> }
        binding.aaa.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.aaa.recyclerView.adapter = mAdapter
        binding.aaa.smartRefreshLayout.setOnRefreshLoadMoreListener(this)

        val ss = SPUtils.getInstance().getString(C.USER_INFO, "")
        val userInfo = Gson().fromJson(ss, UserInfo::class.java)
        binding.tvName.text = userInfo?.username ?: ""
        binding.tvCoins.text = userInfo?.coinCount ?: ""
        val rank = userInfo?.rank ?: ""
        if (!TextUtils.isEmpty(rank)) {
            if (rank == "1" || rank == "2" || rank == "3") {
                binding.ivLogo.visibility = View.VISIBLE
                binding.ivLogo.setImageResource(if (rank == "1") R.mipmap.gold_crown else if (rank == "2") R.mipmap.silver_crown else R.mipmap.cooper_crown)
                binding.tvLogo.visibility = View.GONE
            } else {
                binding.ivLogo.visibility = View.GONE
                binding.tvLogo.visibility = View.VISIBLE
                binding.tvLogo.text = rank
            }
        } else {
            binding.tvLogo.visibility = View.GONE
            binding.ivLogo.visibility = View.GONE
        }

    }

    override fun initData() {
        getList(false)
        viewModel.list.observe(this,{
                if (it.second){
                    mAdapter.addData(it.first?.datas?: listOf())
                }else{
                    mAdapter.setList(it.first?.datas?: listOf())
                }
        })
    }

    override fun onRefresh(refreshLayout: RefreshLayout) {
        getList(false)
    }

    override fun onLoadMore(refreshLayout: RefreshLayout) {
        getList(true)
    }

    private fun getList(isMore: Boolean) {
        binding.aaa.smartRefreshLayout.finishLoadMore()
        binding.aaa.smartRefreshLayout.finishRefresh()
        page = if (isMore) page++ else 1
        viewModel.getList(page,isMore)
    }


}