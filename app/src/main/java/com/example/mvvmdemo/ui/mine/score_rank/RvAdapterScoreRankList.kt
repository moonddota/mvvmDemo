package com.example.mvvmdemo.ui.mine.score_rank

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.example.mvvmdemo.R
import com.example.mvvmdemo.bean.RankBean

class RvAdapterScoreRankList :
    BaseQuickAdapter<RankBean, BaseViewHolder>(R.layout.rv_item_score_rank_list) {

    override fun convert(holder: BaseViewHolder, item: RankBean) {
        val rank: String = item.rank ?: ""
        holder.setGone(R.id.ivLogo, rank != "1" && rank != "2" && rank != "3")
            .setImageResource(
                R.id.ivLogo,
                if (rank == "1") R.mipmap.gold_crown else if (rank == "2") R.mipmap.silver_crown else R.mipmap.cooper_crown
            )
            .setGone(R.id.tvLogo, rank == "1" || rank == "2" || rank == "3")
            .setText(R.id.tvLogo, rank)
            .setText(R.id.tvName, item.username ?: "")
            .setText(R.id.tvCoins, (item.coinCount ?: "").toString() + "")
    }
}