package com.example.mvvmdemo.ui.project

import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mvvmdemo.base.BaseViewModelFragment
import com.example.mvvmdemo.bean.ArticleBean
import com.example.mvvmdemo.bean.ProjectListRes
import com.example.mvvmdemo.databinding.ProjectFragmentBinding
import com.scwang.smart.refresh.layout.api.RefreshLayout
import com.scwang.smart.refresh.layout.simple.SimpleMultiListener

class ProjectFg : BaseViewModelFragment<ProjectVM, ProjectFragmentBinding>() {

    private var page = 0
    private var id = ""

    private val pList by lazy { mutableListOf<ProjectListRes>() }

    private val mAdapter by lazy { ProgectAdapter() }

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
                getList(false)
            }
        })
        viewModel.run {
            data.observe(viewLifecycleOwner, {
                pList.clear()
                pList.addAll(it)
                if (pList.isNotEmpty()) {
                    binding.tvTitle.text = pList[0].name
                    id = pList[0].id ?: ""
                    getList(false)
                }
            })
            list.observe(viewLifecycleOwner, {
                if (it.second) {
                    mAdapter.addData(it.first?.datas ?: mutableListOf())
                } else {
                    mAdapter.setList(it.first?.datas ?: mutableListOf())
                }
            })
        }
    }

    private fun initAdapter() {
        mAdapter.setOnArticleCollect(object : ProgectAdapter.OnArticleCollect {
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
        binding.refreshLayout.setOnMultiListener(object : SimpleMultiListener() {
            override fun onLoadMore(refreshLayout: RefreshLayout) {
                getList(true)
            }

            override fun onRefresh(refreshLayout: RefreshLayout) {
                getList(false)
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