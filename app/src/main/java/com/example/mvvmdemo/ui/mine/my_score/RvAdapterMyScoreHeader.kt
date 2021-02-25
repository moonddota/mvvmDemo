package com.example.mvvmdemo.ui.mine.my_score

import android.animation.ValueAnimator
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.DecelerateInterpolator
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.alibaba.android.vlayout.DelegateAdapter
import com.alibaba.android.vlayout.LayoutHelper
import com.alibaba.android.vlayout.layout.SingleLayoutHelper
import com.blankj.utilcode.util.SPUtils
import com.example.mvvmdemo.R
import com.example.mvvmdemo.bean.UserInfo
import com.example.mvvmdemo.constant.C
import com.google.gson.Gson

class RvAdapterMyScoreHeader : DelegateAdapter.Adapter<RvAdapterMyScoreHeader.ViewHolder>() {

    private var score: String? = null

    fun setScore(score: String?) {
        this.score = score
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.rv_item_my_score_header, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        startAnim(holder.tvScore!!)
    }

    override fun getItemCount(): Int = 1

    override fun onCreateLayoutHelper(): LayoutHelper = SingleLayoutHelper()


    private fun startAnim(textView: TextView) {
        val ss = SPUtils.getInstance().getString(C.USER_INFO, "")
        val coinCount: String = Gson().fromJson(ss, UserInfo::class.java)?.coinCount?:"0"

        val valueAnimator = ValueAnimator.ofInt(0, coinCount.toInt())
        //播放时长
        valueAnimator.duration = C.DURATION
        valueAnimator.interpolator = DecelerateInterpolator()
        valueAnimator.addUpdateListener { valueAnimator1: ValueAnimator ->
            //获取改变后的值
            val currentValue = valueAnimator1.animatedValue as Int
            textView.text = currentValue.toString() + ""
        }
        valueAnimator.start()
    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var tvScore: TextView? = null

        init {
            tvScore = itemView.findViewById(R.id.tvScore)
        }
    }

}