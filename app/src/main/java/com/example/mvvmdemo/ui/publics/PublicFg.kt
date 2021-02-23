package com.example.mvvmdemo.ui.publics

import android.view.LayoutInflater
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mvvmdemo.R
import com.example.mvvmdemo.base.BaseViewModelFragment
import com.example.mvvmdemo.bean.ArticleBean
import com.example.mvvmdemo.databinding.PublicFragmentBinding
import com.example.mvvmdemo.ui.publics.animation.GuillotineAnimation
import com.example.mvvmdemo.widget.hivelayoutmanager.HiveLayoutManager
import com.scwang.smart.refresh.layout.api.RefreshLayout
import com.scwang.smart.refresh.layout.listener.OnRefreshLoadMoreListener

class PublicFg : BaseViewModelFragment<PublicVM, PublicFragmentBinding>() {

    companion object {
        private const val RIPPLE_DURATION: Long = 250
    }

    private var page = 0
    private var id: String = ""

    private val authorAdapter by lazy { AuthorAdapter() }
    private val publicArticleAdapter by lazy { PublicArticleAdapter() }
    private var guillotineAnimation: GuillotineAnimation? = null

    override fun immersionBar(): Boolean = true

    override fun providerVMClass(): Class<PublicVM> = PublicVM::class.java

    override fun initView() {
        initTop()
        initAdapter()
    }

    override fun initData() {
        viewModel.getTopData()
        viewModel.data.observe(viewLifecycleOwner, {
            if (it.second) {
                publicArticleAdapter.addData(it.first?.datas ?: listOf())
            } else {
                publicArticleAdapter.setList(it.first?.datas ?: listOf())
            }
            runLayoutAnimation(binding.aa.recyclerView)
        })
        viewModel.topData.observe(viewLifecycleOwner, {
            authorAdapter.setList(it)
            if (it.isNotEmpty()) {
                binding.tvTitle.text = it[0].name?:""
                id = it[0].id?:""
                getList( false)
            }
        })
    }

    private fun getList(isMore: Boolean) {
        binding.aa.smartRefreshLayout.finishRefresh()
        binding.aa.smartRefreshLayout.finishLoadMore()
        page = if (isMore) page + 1 else 0
        viewModel.getList(id, page, isMore)
    }

    private fun initAdapter() {
        binding.aa.recyclerView.layoutManager = LinearLayoutManager(context)
        binding.aa.recyclerView.adapter = publicArticleAdapter
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
        binding.aa.smartRefreshLayout.setOnRefreshLoadMoreListener(object :
            OnRefreshLoadMoreListener {
            override fun onRefresh(refreshLayout: RefreshLayout) {
                getList(false)
            }

            override fun onLoadMore(refreshLayout: RefreshLayout) {
                getList(true)
            }
        })
    }

    private fun initTop() {
        val guillotineMenu = LayoutInflater.from(context).inflate(R.layout.guillotine, null)
        val rvAuthor = guillotineMenu.findViewById<RecyclerView>(R.id.rvAuthor)
        val layoutManager = HiveLayoutManager(HiveLayoutManager.VERTICAL)
        //        layoutManager.setGravity(HiveLayoutManager.ALIGN_RIGHT);
//        layoutManager.setGravity(HiveLayoutManager.ALIGN_TOP);
//        layoutManager.setGravity(HiveLayoutManager.ALIGN_BOTTOM);
//        layoutManager.setGravity(HiveLayoutManager.ALIGN_LEFT | HiveLayoutManager.ALIGN_TOP);
//        layoutManager.setGravity(HiveLayoutManager.ALIGN_LEFT | HiveLayoutManager.ALIGN_BOTTOM);
//        layoutManager.setGravity(HiveLayoutManager.ALIGN_RIGHT | HiveLayoutManager.ALIGN_TOP);
//        layoutManager.setGravity(HiveLayoutManager.ALIGN_RIGHT | HiveLayoutManager.ALIGN_BOTTOM);
//        layoutManager.setPadding(0, 0, 0, 0);
        rvAuthor.layoutManager = layoutManager
        authorAdapter.setOnItemClickListener { _, _, position ->
            guillotineAnimation?.close()
            binding.tvTitle.text = authorAdapter.data[position].name ?: ""
            id = authorAdapter.data[position].id ?: ""
            getList(false)
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