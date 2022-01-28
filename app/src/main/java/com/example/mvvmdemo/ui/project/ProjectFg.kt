package com.example.mvvmdemo.ui.project

import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.example.mvvmdemo.Paging3.PagingWrapAdapter
import com.example.mvvmdemo.base.BaseViewModelFragment
import com.example.mvvmdemo.bean.ArticleBean
import com.example.mvvmdemo.bean.ProjectListRes
import com.example.mvvmdemo.databinding.ProjectFragmentBinding
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class ProjectFg : BaseViewModelFragment<ProjectVM, ProjectFragmentBinding>() {

    private var id = ""

    private val pList by lazy { mutableListOf<ProjectListRes>() }

    private val progectAdapter by lazy { ProgectAdapter() }

    private val mAdapter by lazy {
        PagingWrapAdapter<ArticleBean, BaseViewHolder>(progectAdapter) {
            progectAdapter.setList(it)
        }
    }

    private val popuWindow by lazy { ProjectPopuWindow(requireActivity()) }

    override fun immersionBar(): Boolean = true

    override fun providerVMClass(): Class<ProjectVM> = ProjectVM::class.java

    override fun initView() {
        binding.tvTitle.setOnClickListener {
            if (popuWindow.isShow())
                popuWindow.dismiss()
            else
                popuWindow.show(binding.tvTitle, pList)
        }
        initAdapter()
    }

    override fun initData() {
        viewModel.getData()
        popuWindow.setOnClick(object : ProjectPopuWindow.PopuClick {
            override fun onItemClick(item: ProjectListRes) {
                binding.tvTitle.text = item.name
                id = item.id ?: ""
                viewModel.getList(id)
            }
        })
        viewModel.run {
            data.observe(viewLifecycleOwner, {
                pList.clear()
                pList.addAll(it)
                if (pList.isNotEmpty()) {
                    binding.tvTitle.text = pList[0].name
                    id = pList[0].id ?: ""
                    viewModel.getList(id)
                }
            })
            list.observe(viewLifecycleOwner, {
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
        progectAdapter.setOnArticleCollect(object : ProgectAdapter.OnArticleCollect {
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
            viewModel.getList(id)
        }
    }

}