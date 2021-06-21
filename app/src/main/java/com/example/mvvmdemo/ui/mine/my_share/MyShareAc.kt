package com.example.mvvmdemo.ui.mine.my_viewLifecycleOwnershare

import android.graphics.Color
import android.view.ViewGroup
import com.alibaba.android.arouter.facade.annotation.Route
import com.example.mvvmdemo.R
import com.example.mvvmdemo.base.BaseViewModelActivity
import com.example.mvvmdemo.bean.PageImpl
import com.example.mvvmdemo.constant.RouterActivityPath
import com.example.mvvmdemo.databinding.MyShareActivityBinding
import com.example.mvvmdemo.ui.mine.my_share.MyShareVM
import com.example.mvvmdemo.ui.square.RvAdapterArticleList
import com.example.mvvmdemo.util.ARouterUtil
import com.example.mvvmdemo.widget.CustomItemDecoration
import com.scwang.smart.refresh.layout.api.RefreshLayout
import com.scwang.smart.refresh.layout.listener.OnRefreshLoadMoreListener
import com.yanzhenjie.recyclerview.SwipeMenuItem
import com.yanzhenjie.recyclerview.SwipeRecyclerView

@Route(path = RouterActivityPath.MyShare.MyShareAc)
class MyShareAc : BaseViewModelActivity<MyShareVM, MyShareActivityBinding>(),
    OnRefreshLoadMoreListener {

    private val pageImpl by lazy { PageImpl() }
    private val mAdapter by lazy { RvAdapterArticleList() }

    override fun providerVMClass(): Class<MyShareVM> = MyShareVM::class.java

    override fun initView() {
        binding.tvTitle.text = getString(R.string.mine_share)
        binding.ivLeft.setOnClickListener { finish() }

        binding.fab.setOnClickListener {
            ARouterUtil.jumpShareArticleAc()
        }

        binding.recyclerView.addItemDecoration(
            CustomItemDecoration(
                this,
                CustomItemDecoration.ItemDecorationDirection.VERTICAL_LIST,
                R.drawable.linear_split_line
            )
        )
        binding.recyclerView.isSwipeItemMenuEnabled = true
        binding.recyclerView.setSwipeMenuCreator { _, rightMenu, _ ->
            val width = resources.getDimensionPixelSize(R.dimen.dp_70)
            // 1. MATCH_PARENT 自适应高度，保持和Item一样高;
            // 2. 指定具体的高，比如80;
            // 3. WRAP_CONTENT，自身高度，不推荐;
            val height = ViewGroup.LayoutParams.MATCH_PARENT
            val deleteItem: SwipeMenuItem = SwipeMenuItem(this@MyShareAc)
                .setText("删除")
                .setTextColor(Color.WHITE)
                .setBackgroundColorResource(android.R.color.holo_red_dark)
                .setWidth(width)
                .setHeight(height)
            rightMenu.addMenuItem(deleteItem) // 在Item右侧添加一个菜单。
            // 注意：哪边不想要菜单，那么不要添加即可。
        }
        binding.recyclerView.setOnItemMenuClickListener { menuBridge, adapterPosition ->
            menuBridge.closeMenu()
            val direction: Int = menuBridge.getDirection() // 左侧还是右侧菜单。
            val menuPosition: Int = menuBridge.getPosition() // 菜单在RecyclerView的Item中的Position。
            if (direction == SwipeRecyclerView.RIGHT_DIRECTION) {
                if (menuPosition == 0) {
                    viewModel.deleteArticle(mAdapter.data[adapterPosition].id ?: "")
                    mAdapter.cancelCollect(adapterPosition)
                }
            }
        }
        binding.recyclerView.adapter = mAdapter

        binding.smartRefreshLayout.setOnRefreshLoadMoreListener(this)
        mAdapter.setOnItemChildClickListener { adapter, view, position ->
            if (view.id == R.id.ivCollect) {
                val articleBean = mAdapter.data[position]
                if (articleBean.collect == true) {
                    viewModel.unCollect(articleBean.id ?: "")
                } else {
                    viewModel.collect(articleBean.id ?: "")
                }
                articleBean.collect = !(articleBean.collect ?: false)
                mAdapter.notifyItemChanged(position)
            }
        }
        mAdapter.setOnItemClickListener { adapter, view, position ->
            ARouterUtil.jumpWeb(mAdapter.data[position].link ?: "")
        }

    }

    override fun initData() {
        viewModel.data.observe(this, {
            val shareArticles = it.first?.shareArticles
            if (it.second) {
                mAdapter.addData(shareArticles?.datas ?: listOf())
            } else {
                mAdapter.setList(shareArticles?.datas ?: listOf())
            }
            binding.smartRefreshLayout.setEnableLoadMore(shareArticles?.curPage ?: 0 < shareArticles?.pageCount ?: 0)
        })
    }

    override fun onResume() {
        super.onResume()
        getList(false)
    }

    override fun onRefresh(refreshLayout: RefreshLayout) {
        getList(false)
    }

    override fun onLoadMore(refreshLayout: RefreshLayout) {
        getList(true)
    }

    private fun getList(isMore: Boolean) {
        binding.smartRefreshLayout.finishRefresh()
        binding.smartRefreshLayout.finishLoadMore()
        if (isMore) {
            pageImpl.nextPage()
        } else {
            pageImpl.reset()
        }
        viewModel.getList(pageImpl.page, isMore)
    }

}