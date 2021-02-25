package com.example.mvvmdemo.ui.home

import android.graphics.Color
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import com.blankj.utilcode.util.ConvertUtils.dp2px
import com.blankj.utilcode.util.LogUtils
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.example.mvvmdemo.R
import com.example.mvvmdemo.base.BaseViewModelFragment
import com.example.mvvmdemo.bean.ArticleBean
import com.example.mvvmdemo.bean.BannerRes
import com.example.mvvmdemo.databinding.HomeFragmentBinding
import com.example.mvvmdemo.util.ARouterUtil
import com.scwang.smart.refresh.layout.api.RefreshHeader
import com.scwang.smart.refresh.layout.api.RefreshLayout
import com.scwang.smart.refresh.layout.constant.RefreshState
import com.scwang.smart.refresh.layout.simple.SimpleMultiListener
import com.to.aboomy.pager2banner.Banner
import com.to.aboomy.pager2banner.IndicatorView
import com.to.aboomy.pager2banner.ScaleInTransformer
import java.lang.String

class HomeFg : BaseViewModelFragment<HomeFgViewModel, HomeFragmentBinding>() {

    private var page = 0

    private val glide by lazy { Glide.with(requireContext()) }

    private val mAdapter by lazy { HomeAdapter() }
    private lateinit var bannerAdapter: BaseQuickAdapter<BannerRes, BaseViewHolder>

    override fun immersionBar(): Boolean = true

    override fun providerVMClass(): Class<HomeFgViewModel> = HomeFgViewModel::class.java

    override fun initView() {
        initAdapter()

        binding.statusView.showLoading()
        viewModel.getBanner()
        getList(false)
    }

    override fun initData() {
        viewModel.run {
            bannerList.observe(viewLifecycleOwner, {
                binding.statusView.showContent()
                bannerAdapter.setList(it)
            })
            listData.observe(viewLifecycleOwner, {
                if (it.second) {
                    mAdapter.addData(it.first?.datas ?: mutableListOf())
                } else {
                    mAdapter.setList(it.first?.datas ?: mutableListOf())
                }
            })
        }
    }

    private fun initAdapter() {
        mAdapter.setOnArticleCollect(object : HomeAdapter.OnArticleCollect {
            override fun onCollect(item: ArticleBean) {
                if (item.collect == true) {
                    viewModel.unCollect(item.id ?: "")
                } else {
                    viewModel.collect(item.id ?: "")
                }
            }
        })
        binding.rc.layoutManager = LinearLayoutManager(requireContext())
        binding.rc.adapter = mAdapter
        initHeader()
        binding.refreshLayout.setOnMultiListener(object : SimpleMultiListener() {
            override fun onLoadMore(refreshLayout: RefreshLayout) {
                getList(true)
            }

            override fun onRefresh(refreshLayout: RefreshLayout) {
                getList(false)
            }

            override fun onHeaderMoving(
                header: RefreshHeader?,
                isDragging: Boolean,
                percent: Float,
                offset: Int,
                headerHeight: Int,
                maxDragHeight: Int
            ) {
                binding.topbar.alpha = 1 - Math.min(percent, 1f)
                binding.secondFloor.translationY = Math.min(
                    offset - binding.secondFloor.height + binding.topbar.height,
                    binding.refreshLayout.layout
                        .height - binding.secondFloor.height
                ).toFloat()
            }

            override fun onStateChanged(
                refreshLayout: RefreshLayout,
                oldState: RefreshState,
                newState: RefreshState
            ) {
                if (oldState == RefreshState.TwoLevel) {
                    binding.secondFloorContent.animate().alpha(0f).setDuration(1000)
                }
            }
        })

        binding.header.setOnTwoLevelListener {
            LogUtils.e("触发二楼事件")
            binding.secondFloorContent.animate().alpha(1f).setDuration(2000)
            true //true 将会展开二楼状态 false 关闭刷新
        }
    }

    private fun initHeader() {
        bannerAdapter =
            object : BaseQuickAdapter<BannerRes, BaseViewHolder>(R.layout.item_home_banner) {
                override fun convert(holder: BaseViewHolder, item: BannerRes) {
                    holder.getView<ImageView>(R.id.img).apply {
                        glide.load(item.imagePath ?: "")
                            .into(this)
                        setOnClickListener {
                            ARouterUtil.jumpWeb(item.url ?: "")
                        }
                    }
                }
            }
        val headView = layoutInflater.inflate(R.layout.home_headerview, null, false)
        val banner = headView.findViewById<Banner>(R.id.banner)
        banner.setIndicator(IndicatorView(context).apply {
            setIndicatorColor(Color.DKGRAY)
            setIndicatorSelectorColor(Color.WHITE)
        })
        banner.adapter = bannerAdapter
        banner.isAutoPlay = true
        //设置左右页面露出来的宽度及item与item之间的宽度
        banner.setPageMargin(dp2px(20f), dp2px(10f))
        //内置ScaleInTransformer，设置切换缩放动画
        banner.addPageTransformer(ScaleInTransformer())

        mAdapter.addHeaderView(headView)
    }

    private fun getList(isLoadMore: Boolean) {
        binding.refreshLayout.finishRefresh()
        binding.refreshLayout.finishLoadMore()
        page = if (isLoadMore) page + 1 else 0
        viewModel.listArticle(page, isLoadMore)
    }

}