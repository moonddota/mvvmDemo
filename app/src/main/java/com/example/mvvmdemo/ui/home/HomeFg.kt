package com.example.mvvmdemo.ui.home

import android.graphics.Color
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import com.blankj.utilcode.util.ConvertUtils.dp2px
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.example.mvvmdemo.R
import com.example.mvvmdemo.base.BaseViewModelFragment
import com.example.mvvmdemo.bean.BannerRes
import com.example.mvvmdemo.databinding.HomeFragmentBinding
import com.to.aboomy.pager2banner.Banner
import com.to.aboomy.pager2banner.IndicatorView
import com.to.aboomy.pager2banner.ScaleInTransformer

class HomeFg : BaseViewModelFragment<HomeFgViewModel, HomeFragmentBinding>() {

    private val headView by lazy {
        layoutInflater.inflate(R.layout.home_headerview, binding.rc.parent as ViewGroup, false)
    }

    private val glide by lazy { Glide.with(requireContext()) }

    private lateinit var mAdapter: BaseQuickAdapter<Int, BaseViewHolder>
    private lateinit var bannerAdapter: BaseQuickAdapter<BannerRes, BaseViewHolder>

    override fun immersionBar(): Boolean {
        return true
    }

    override fun providerVMClass(): Class<HomeFgViewModel> = HomeFgViewModel::class.java

    override fun initView() {
        initAdapter()

        showLoading(binding.root)
        viewModel.getBanner()
    }

    override fun initData() {
        viewModel.run {
            bannerList.observe(viewLifecycleOwner, {
                bannerAdapter.setList(it)
            })
        }
    }

    private fun initAdapter() {
        mAdapter = object : BaseQuickAdapter<Int, BaseViewHolder>(R.layout.rv_item_home_article) {
            override fun convert(holder: BaseViewHolder, item: Int) {

            }
        }
        binding.rc.layoutManager = LinearLayoutManager(requireContext())
        binding.rc.adapter = mAdapter
        initHeader()
    }

    private fun initHeader() {
        bannerAdapter =
            object : BaseQuickAdapter<BannerRes, BaseViewHolder>(R.layout.item_home_banner) {
                override fun convert(holder: BaseViewHolder, item: BannerRes) {
                    holder.getView<ImageView>(R.id.img).apply {
                        glide.load(item.imagePath ?: "")
                            .into(this)
                    }
                }
            }
        bannerAdapter.setOnItemClickListener { adapter, view, position ->

        }

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


}