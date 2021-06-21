package com.example.mvvmdemo.Paging3

import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.alibaba.android.arouter.facade.annotation.Route
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.example.mvvmdemo.base.BaseViewModelActivity
import com.example.mvvmdemo.constant.RouterActivityPath
import com.example.mvvmdemo.databinding.ActivityPaging3Binding
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@Route(path = RouterActivityPath.Paging3.Paging3Ac)
class Paging3Ac : BaseViewModelActivity<PagingViewModel, ActivityPaging3Binding>() {

    private val mAdapter by lazy {
        val readDemoAdapter = RepoDemoAdapter()
        PagingWrapAdapter<Repo, BaseViewHolder>(readDemoAdapter) {
            readDemoAdapter.setList(it)
        }
    }

    override fun providerVMClass(): Class<PagingViewModel> = PagingViewModel::class.java

    override fun initView() {
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = mAdapter
        binding.refreshLayout.setOnRefreshListener{
            viewModel.getPagingData()
        }

    }

    override fun initData() {
        viewModel.getPagingData()
        viewModel.PagingData.observe(this) {
            lifecycleScope.launch {
                it.collect { pagingData ->
                    mAdapter.submitList(pagingData)
                }
            }
            binding.refreshLayout.finishRefresh()
            binding.refreshLayout.finishLoadMore()
        }
    }

}