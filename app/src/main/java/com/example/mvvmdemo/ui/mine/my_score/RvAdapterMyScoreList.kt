package com.example.mvvmdemo.ui.mine.my_score

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.alibaba.android.vlayout.DelegateAdapter
import com.alibaba.android.vlayout.LayoutHelper
import com.alibaba.android.vlayout.layout.SingleLayoutHelper
import com.example.mvvmdemo.R
import com.example.mvvmdemo.bean.RankBean
import java.lang.String
import java.text.SimpleDateFormat
import java.util.*

class RvAdapterMyScoreList(val dataList: List<RankBean>) :
    DelegateAdapter.Adapter<RvAdapterMyScoreList.ViewHolder>() {

    private val simpleDateFormat by lazy { SimpleDateFormat("yyyy-MM-dd HH:mm:ss") }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(
                parent.context
            ).inflate(R.layout.rv_item_my_score_list, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val rankBean = dataList[position]
        holder.tvScore!!.text = String.format("+%s", rankBean.coinCount ?: "")
        holder.tvTitle!!.text = rankBean.reason ?: ""
        holder.tvTime!!.text = simpleDateFormat.format(Date(rankBean.date ?: 0))
    }

    override fun getItemCount(): Int = dataList.size

    override fun onCreateLayoutHelper(): LayoutHelper = SingleLayoutHelper()

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var llItem: LinearLayout? = null
        var tvTitle: TextView? = null
        var tvTime: TextView? = null
        var tvScore: TextView? = null

        init {
            llItem = itemView.findViewById(R.id.llItem)
            tvTitle = itemView.findViewById(R.id.tvTitle)
            tvTime = itemView.findViewById(R.id.tvTime)
            tvScore = itemView.findViewById(R.id.tvScore)
        }
    }

}