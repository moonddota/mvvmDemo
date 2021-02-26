package com.example.mvvmdemo.ui.mine.my_score

import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.vlayout.DelegateAdapter
import com.alibaba.android.vlayout.VirtualLayoutManager
import com.example.mvvmdemo.R
import com.example.mvvmdemo.base.BaseViewModelActivity
import com.example.mvvmdemo.bean.RankBean
import com.example.mvvmdemo.constant.RouterActivityPath
import com.example.mvvmdemo.databinding.MyScoreActivityBinding
import com.scwang.smart.refresh.layout.api.RefreshLayout
import com.scwang.smart.refresh.layout.listener.OnRefreshLoadMoreListener

@Route(path = RouterActivityPath.MyScore.MYSCOREAC)
class MyScoreAc : BaseViewModelActivity<MyScoreVM, MyScoreActivityBinding>(),
    OnRefreshLoadMoreListener {

    private var page = 1
    private val adapterMyScoreHeader by lazy { RvAdapterMyScoreHeader() }
    private val adapterMyScoreList by lazy { RvAdapterMyScoreList(dataList) }
    private val dataList by lazy { arrayListOf<RankBean>() }
    private val adapters by lazy { arrayListOf<DelegateAdapter.Adapter<*>>() }
    private val virtualLayoutManager by lazy { VirtualLayoutManager(this) }
    private val delegateAdapter by lazy { DelegateAdapter(virtualLayoutManager) }


    override fun providerVMClass(): Class<MyScoreVM> = MyScoreVM::class.java
    override fun initView() {
        binding.topbar.tvTitle.text = "我的积分"
        binding.topbar.ivLeft.setOnClickListener { finish() }
        with(binding.topbar.ivRight) {
            visibility = View.VISIBLE
            setBackgroundResource(R.mipmap.ic_question)
//            RouterUtil.launchWeb(C.INTERGRAL_URL)
        }

        adapters.add(adapterMyScoreHeader)
        adapters.add(adapterMyScoreList)
        delegateAdapter.setAdapters(adapters)
        binding.aaa.recyclerView.layoutManager = virtualLayoutManager
        binding.aaa.recyclerView.adapter = delegateAdapter
        binding.aaa.smartRefreshLayout.setOnRefreshLoadMoreListener(this)
    }

    override fun initData() {
        getList(false)
        viewModel.data.observe(this, {
            if (it.second) {
                dataList.clear()
            }
           val baen = it.first
            dataList.addAll(baen?.datas?: listOf())
            adapterMyScoreList.notifyDataSetChanged()
            binding.aaa.smartRefreshLayout.setEnableLoadMore(baen?.curPage ?: 0 < baen?.pageCount ?: 0)
        })
    }

    override fun onRefresh(refreshLayout: RefreshLayout) {
        getList(false)
    }

    override fun onLoadMore(refreshLayout: RefreshLayout) {
        getList(true)
    }

    private fun getList(isMore: Boolean) {
        binding.aaa.smartRefreshLayout.finishRefresh()
        binding.aaa.smartRefreshLayout.finishLoadMore()
        page = if (isMore) page++ else 1
        viewModel.getList(page, isMore)
    }

}