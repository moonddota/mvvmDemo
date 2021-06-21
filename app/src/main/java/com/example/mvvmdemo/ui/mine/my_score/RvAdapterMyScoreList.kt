package com.example.mvvmdemo.ui.mine.my_score

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.example.mvvmdemo.R
import com.example.mvvmdemo.bean.RankBean
import java.lang.String
import java.text.SimpleDateFormat
import java.util.*

class RvAdapterMyScoreList :
    BaseQuickAdapter<RankBean, BaseViewHolder>(R.layout.rv_item_my_score_list) {

    private val simpleDateFormat by lazy { SimpleDateFormat("yyyy-MM-dd HH:mm:ss") }

    override fun convert(holder: BaseViewHolder, item: RankBean) {
        holder.setText(R.id.tvScore, String.format("+%s", item.coinCount ?: ""))
            .setText(R.id.tvTitle, item.reason ?: "")
            .setText(R.id.tvTime, simpleDateFormat.format(Date(item.date ?: 0)))
    }

}