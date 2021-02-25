package com.example.mvvmdemo.ui.mine.my_collect

import androidx.recyclerview.widget.LinearLayoutManager
import com.alibaba.android.arouter.facade.annotation.Route
import com.example.mvvmdemo.R
import com.example.mvvmdemo.base.BaseViewModelActivity
import com.example.mvvmdemo.bean.ArticleBean
import com.example.mvvmdemo.constant.RouterActivityPath
import com.example.mvvmdemo.databinding.MyCollectActivityBinding
import com.example.mvvmdemo.ui.square.RvAdapterArticleList
import com.example.mvvmdemo.util.ARouterUtil
import com.example.mvvmdemo.widget.CustomItemDecoration
import com.scwang.smart.refresh.layout.api.RefreshLayout
import com.scwang.smart.refresh.layout.listener.OnRefreshLoadMoreListener

@Route(path = RouterActivityPath.MyCollect.MyCollectAc)
class MyCollectAc : BaseViewModelActivity<MyCollectVM, MyCollectActivityBinding>(),
    OnRefreshLoadMoreListener {

    private val mAdapter by lazy { RvAdapterArticleList() }

    private var page = 0

    override fun providerVMClass(): Class<MyCollectVM> = MyCollectVM::class.java

    override fun initView() {
        binding.topbar.tvTitle.text = getString(R.string.mine_collect)
        binding.topbar.ivLeft.setOnClickListener { finish() }

        binding.aaa.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.aaa.recyclerView.adapter = mAdapter
        binding.aaa.recyclerView.addItemDecoration(
            CustomItemDecoration(
                this,
                CustomItemDecoration.ItemDecorationDirection.VERTICAL_LIST,
                R.drawable.linear_split_line
            )
        )
        mAdapter.setOnItemClickListener { adapter, view, position ->
            ARouterUtil.jumpWeb(mAdapter.data[position].link ?: "")
        }
        mAdapter.setOnItemChildClickListener { adapter, view, position ->
            if (view.id == R.id.ivCollect) {
                viewModel.unCollect(mAdapter.data[position].id ?: "")
                mAdapter.cancelCollect(position)
            }
        }
        binding.aaa.smartRefreshLayout.setOnRefreshLoadMoreListener(this)

    }

    override fun initData() {
        getList(false)
        viewModel.list.observe(this, {
            val datas: List<ArticleBean> = it.first?.datas ?: listOf()
            datas.forEach { data ->
                data.collect = true
            }
            if (it.second) {
                mAdapter.addData(datas)
            } else {
                mAdapter.setList(datas)
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
        page = if (isMore) page++ else 0
        viewModel.getList(page, isMore)
    }


}