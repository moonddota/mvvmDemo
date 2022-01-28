package com.example.mvvmdemo.ui.publics

import android.view.LayoutInflater
import android.view.animation.AnimationUtils
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.example.mvvmdemo.Paging3.PagingWrapAdapter
import com.example.mvvmdemo.R
import com.example.mvvmdemo.base.BaseViewModelFragment
import com.example.mvvmdemo.bean.ArticleBean
import com.example.mvvmdemo.databinding.PublicFragmentBinding
import com.example.mvvmdemo.ui.publics.animation.GuillotineAnimation
import com.example.mvvmdemo.widget.hivelayoutmanager.HiveLayoutManager
import com.scwang.smart.refresh.layout.api.RefreshLayout
import com.scwang.smart.refresh.layout.listener.OnRefreshLoadMoreListener
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class PublicFg : BaseViewModelFragment<PublicVM, PublicFragmentBinding>() {

    companion object {
        private const val RIPPLE_DURATION: Long = 250
    }

    private var id: String = ""

    private val authorAdapter by lazy { AuthorAdapter() }
    private val publicArticleAdapter by lazy { PublicArticleAdapter() }
    private var guillotineAnimation: GuillotineAnimation? = null

    private val mAdapter by lazy {
        PagingWrapAdapter<ArticleBean, BaseViewHolder>(publicArticleAdapter) {
            publicArticleAdapter.setList(it)
        }
    }

    override fun immersionBar(): Boolean = true

    override fun providerVMClass(): Class<PublicVM> = PublicVM::class.java

    override fun initView() {
        initTop()
        initAdapter()
    }

    override fun initData() {
        viewModel.getTopData()
        viewModel.data.observe(viewLifecycleOwner, {
            lifecycleScope.launch {
                it.collect { pagingData ->
                    mAdapter.submitList(pagingData)
                }
            }
            binding.aa.smartRefreshLayout.finishRefresh()
            binding.aa.smartRefreshLayout.finishLoadMore()
            runLayoutAnimation(binding.aa.recyclerView)
        })
        viewModel.topData.observe(viewLifecycleOwner, {
            authorAdapter.setList(it)
            if (it.isNotEmpty()) {
                binding.tvTitle.text = it[0].name ?: ""
                id = it[0].id ?: ""
                viewModel.listArticle(id)
            }
        })
    }

    private fun initAdapter() {
        binding.aa.recyclerView.layoutManager = LinearLayoutManager(context)
        binding.aa.recyclerView.adapter = mAdapter
        publicArticleAdapter.setOnArticleCollect(object : PublicArticleAdapter.OnArticleCollect {
            override fun onCollect(item: ArticleBean) {
                if (item.collect == true) {
                    viewModel.unCollect(item.id ?: "")
                } else {
                    viewModel.collect(item.id ?: "")
                }
            }
        })
        binding.aa.smartRefreshLayout.setEnableLoadMore(true)
        binding.aa.smartRefreshLayout.setEnableRefresh(true)
        binding.aa.smartRefreshLayout.setOnRefreshListener {
            viewModel.listArticle(id)
        }
    }

    private fun initTop() {
        val guillotineMenu = LayoutInflater.from(context).inflate(R.layout.guillotine, null)
        val rvAuthor = guillotineMenu.findViewById<RecyclerView>(R.id.rvAuthor)
        val layoutManager = HiveLayoutManager(HiveLayoutManager.VERTICAL)
        rvAuthor.layoutManager = layoutManager
        authorAdapter.setOnItemClickListener { _, _, position ->
            guillotineAnimation?.close()
            if (id != authorAdapter.data[position].id ?: "") {
                binding.tvTitle.text = authorAdapter.data[position].name ?: ""
                id = authorAdapter.data[position].id ?: ""
                viewModel.listArticle(id)
            }
        }
        rvAuthor.adapter = authorAdapter
        binding.root.addView(guillotineMenu)
        guillotineAnimation = GuillotineAnimation.GuillotineBuilder(
            guillotineMenu,
            guillotineMenu.findViewById(R.id.ivHamburger),
            binding.ivClose
        )
            .setStartDelay(RIPPLE_DURATION)
            .setActionBarViewForAnimation(binding.toolbar1)
            .setClosedOnStart(true)
            .build()
    }

    private fun runLayoutAnimation(recyclerView: RecyclerView) {
        val context = recyclerView.context
        val controller =
            AnimationUtils.loadLayoutAnimation(context, R.anim.layout_animation_from_bottom)
        recyclerView.layoutAnimation = controller
        recyclerView.adapter!!.notifyDataSetChanged()
        recyclerView.scheduleLayoutAnimation()
    }

}