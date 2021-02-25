package com.example.mvvmdemo.ui.mine.open_source

import android.view.View
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.example.mvvmdemo.R
import com.example.mvvmdemo.bean.OpenSourcePro
import com.example.mvvmdemo.util.ARouterUtil

class RvAdapterOpenSourcePro :
    BaseQuickAdapter<OpenSourcePro, BaseViewHolder>(R.layout.rv_item_open_source_pro) {
    override fun convert(holder: BaseViewHolder, item: OpenSourcePro) {
        holder.setText(R.id.tvTitle, item.author ?: "")
            .setText(R.id.tvContent, item.content ?: "")
        holder.itemView.setOnClickListener(View.OnClickListener { view: View? ->
            ARouterUtil.jumpWeb(item.link ?: "")
        })
    }
}