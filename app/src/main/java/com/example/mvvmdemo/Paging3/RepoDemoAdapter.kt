package com.example.mvvmdemo.Paging3

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.example.mvvmdemo.R

class RepoDemoAdapter:BaseQuickAdapter<Repo,BaseViewHolder>(R.layout.repo_item) {
    override fun convert(holder: BaseViewHolder, item: Repo) {
        holder.setText(R.id.name_text,item.name)
        holder.setText(R.id.description_text,item.description)
        holder.setText(R.id.star_count_text,item.starCount.toString())
    }
}