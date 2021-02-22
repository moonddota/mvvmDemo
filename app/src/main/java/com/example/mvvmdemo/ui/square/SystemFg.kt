package com.example.mvvmdemo.ui.square

import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import androidx.appcompat.widget.AppCompatTextView
import com.example.mvvmdemo.R
import com.example.mvvmdemo.base.BaseViewModelFragment
import com.example.mvvmdemo.base.ChildrenBean
import com.example.mvvmdemo.bean.ArticleBean
import com.example.mvvmdemo.databinding.SystemFragmentBinding
import com.example.mvvmdemo.util.ARouterUtil
import com.example.mvvmdemo.util.ResourceUtil
import com.example.mvvmdemo.widget.indicatorview.IndicatorItem
import com.google.android.flexbox.FlexboxLayout

class SystemFg(val tag: Int) : BaseViewModelFragment<SystemVM, SystemFragmentBinding>() {
    companion object {
        const val SYSTEM_TAG = 0
        const val NAVIGATION_TAG = 1
    }

    private val colorList by lazy { arrayListOf<Int>() }

    override fun providerVMClass(): Class<SystemVM> = SystemVM::class.java

    override fun initView() {
        initColors()
        binding.indicatorScrollView.bindIndicatorView(binding.indicatorView)
    }

    override fun initData() {
        when (tag) {
            SYSTEM_TAG -> viewModel.listTrees()
            NAVIGATION_TAG -> viewModel.listNavis()
        }
        viewModel.data.observe(viewLifecycleOwner, { list ->
            binding.indicatorView.removeAllViews()
            binding.llParent.removeAllViews()
            list.forEachIndexed { i, it ->
                val firstName =
                    if (TextUtils.isEmpty(it.name ?: "")) ""
                    else (it.name ?: "").substring(0, 1)
                val view: View = findItem()
                val tvTitle = view.findViewById<TextView>(R.id.tvTitle)
                tvTitle.text = it.name ?: ""
                val flexboxLayout = view.findViewById<FlexboxLayout>(R.id.flexLayout)
                if (tag == SYSTEM_TAG) {
                    val children: List<ChildrenBean> = it.children ?: listOf()
                    for (child in children) {
                        val textView: AppCompatTextView = findLabel(flexboxLayout)
                        textView.text = child.name ?: ""
                        textView.setOnClickListener { v: View? ->
                            ARouterUtil.jumpArticleList(
                                child.id?:"",
                                child.name?:""
                            )
                        }
                        flexboxLayout.addView(textView)
                    }
                } else {
                    val articles: List<ArticleBean> = it.articles ?: listOf()
                    for (article in articles) {
                        val textView = findLabel(flexboxLayout)
                        textView.text = article.title ?: ""
                        textView.setOnClickListener { v: View? ->
                            ARouterUtil.jumpWeb(
                                article.link?:""
                            )
                        }
                        flexboxLayout.addView(textView)
                    }
                }
                binding.llParent.addView(view)
                val i1: Int = i % colorList.size
                binding.indicatorView.addIndicatorItem(
                    IndicatorItem.Builder(view).setItemText(firstName)
                        .setItemColorResource(colorList[i1])
                        .setItemIconResource(R.mipmap.ic_uncollect).build()
                )

            }

        })
    }

    private fun initColors() {
        for (i in 0..18) {
            val resId: Int = ResourceUtil.getResId("in" + (i + 1), R.color::class.java)
            colorList.add(resId)
        }
    }

    private fun findItem(): View {
        return LayoutInflater.from(context).inflate(R.layout.rv_item_flex, null, false) as View
    }

    private fun findLabel(flexboxLayout: FlexboxLayout): AppCompatTextView {
        return LayoutInflater.from(context)
            .inflate(R.layout.flextlayout_item_label, flexboxLayout, false) as AppCompatTextView
    }

}