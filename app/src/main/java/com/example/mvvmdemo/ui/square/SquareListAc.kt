package com.example.mvvmdemo.ui.square

import androidx.recyclerview.widget.LinearLayoutManager
import com.alibaba.android.arouter.facade.annotation.Route
import com.example.mvvmdemo.R
import com.example.mvvmdemo.base.BaseViewModelActivity
import com.example.mvvmdemo.constant.RouterActivityPath
import com.example.mvvmdemo.databinding.SquareListActivityBinding
import com.example.mvvmdemo.util.ARouterUtil
import com.example.mvvmdemo.widget.CustomItemDecoration
import com.scwang.smart.refresh.layout.api.RefreshLayout
import com.scwang.smart.refresh.layout.simple.SimpleMultiListener

@Route(path = RouterActivityPath.Square.PAGER_SQUARE_LIST)
class SquareListAc : BaseViewModelActivity<SquareListVM, SquareListActivityBinding>() {
    private var page = 0
    private var id: String = ""
    private var title: String = ""

    private val mAdapter by lazy { RvAdapterArticleList() }
    override fun providerVMClass(): Class<SquareListVM> = SquareListVM::class.java

    override fun initView() {
        id = intent.getStringExtra("id") ?: ""
        title = intent.getStringExtra("title") ?: ""
        binding.topbar.tvTitle.text = title
        binding.topbar.ivLeft.setOnClickListener { finish() }
        binding.rc.layoutManager = LinearLayoutManager(this)
        binding.rc.adapter = mAdapter
        binding.rc.addItemDecoration(
            CustomItemDecoration(
                this,
                CustomItemDecoration.ItemDecorationDirection.VERTICAL_LIST,
                R.drawable.linear_split_line
            )
        )
        mAdapter.setOnItemChildClickListener { adapter, view, position ->
            if (view.id == R.id.ivCollect) {
                val item = mAdapter.data[position]
                if (item.collect == true) {
                    viewModel.unCollect(item.id ?: "")
                } else {
                    viewModel.collect(item.id ?: "")
                }
                item.collect = !(item.collect ?: false)
                mAdapter.notifyItemChanged(position)
            }
        }
        mAdapter.setOnItemClickListener { _, _, position ->
            ARouterUtil.jumpWeb(mAdapter.data[position].link ?: "")
        }
        binding.refreshLayout.setEnableLoadMore(true)
        binding.refreshLayout.setEnableRefresh(true)
        binding.refreshLayout.setOnMultiListener(object : SimpleMultiListener() {
            override fun onLoadMore(refreshLayout: RefreshLayout) {
                getList(true)
            }

            override fun onRefresh(refreshLayout: RefreshLayout) {
                getList(false)
            }
        })
    }

    override fun initData() {
        getList(false)
        viewModel.dataList.observe(this, {
            if (it.second) {
                mAdapter.addData(it.first?.datas ?: mutableListOf())
            } else {
                mAdapter.setList(it.first?.datas ?: mutableListOf())
            }
        })
    }

    private fun getList(isMore: Boolean) {
        binding.refreshLayout.finishRefresh()
        binding.refreshLayout.finishLoadMore()
        page = if (isMore) page + 1 else 0
        viewModel.getList(page, id, isMore)
    }

}