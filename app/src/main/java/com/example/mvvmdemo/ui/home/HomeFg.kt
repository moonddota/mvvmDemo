package com.example.mvvmdemo.ui.home

import android.graphics.Color
import android.widget.ImageView
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.blankj.utilcode.util.ConvertUtils.dp2px
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.example.mvvmdemo.Paging3.PagingWrapAdapter
import com.example.mvvmdemo.R
import com.example.mvvmdemo.base.BaseViewModelFragment
import com.example.mvvmdemo.bean.ArticleBean
import com.example.mvvmdemo.bean.BannerRes
import com.example.mvvmdemo.databinding.HomeFragmentBinding
import com.example.mvvmdemo.util.ARouterUtil
import com.to.aboomy.pager2banner.IndicatorView
import com.to.aboomy.pager2banner.ScaleInTransformer
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class HomeFg : BaseViewModelFragment<HomeFgViewModel, HomeFragmentBinding>() {

    private var page = 0

    private val glide by lazy { Glide.with(requireContext()) }

    private val homeAdapter by lazy { HomeAdapter() }
    private val mAdapter by lazy {
        PagingWrapAdapter<ArticleBean, BaseViewHolder>(homeAdapter) {
            homeAdapter.setList(it)
        }
    }


    private lateinit var bannerAdapter: BaseQuickAdapter<BannerRes, BaseViewHolder>

    override fun immersionBar(): Boolean = true

    override fun providerVMClass(): Class<HomeFgViewModel> = HomeFgViewModel::class.java

    override fun initView() {
        initHeader()
        initAdapter()
    }

    override fun initData() {
        viewModel.getBanner()
        viewModel.listArticle()
        viewModel.run {
            bannerList.observe(viewLifecycleOwner, {
                bannerAdapter.setList(it)
            })
            listData.observe(viewLifecycleOwner, {
                lifecycleScope.launch {
                    it.collect { pagingData ->
                        mAdapter.submitList(pagingData)
                    }
                }
                binding.refreshLayout.finishRefresh()
                binding.refreshLayout.finishLoadMore()
            })
        }
    }

    private fun initAdapter() {
        homeAdapter.setOnArticleCollect(object : HomeAdapter.OnArticleCollect {
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
        binding.refreshLayout.setOnRefreshListener {
            viewModel.listArticle()
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
        val banner = binding.headView.banner
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
    }

}