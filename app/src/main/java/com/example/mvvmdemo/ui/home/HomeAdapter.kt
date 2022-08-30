package com.example.mvvmdemo.ui.home

import android.text.TextUtils
import android.view.View
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.listener.OnItemChildClickListener
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.example.mvvmdemo.R
import com.example.mvvmdemo.bean.ArticleBean
import com.example.mvvmdemo.util.ARouterUtil
import com.example.mvvmdemo.widget.shinebutton.ShineButton
import java.lang.String

class HomeAdapter : BaseQuickAdapter<ArticleBean, BaseViewHolder>(R.layout.rv_item_home_article),
    OnItemChildClickListener {

    init {
        addChildClickViewIds(R.id.vItem, R.id.ivCollect)
        setOnItemChildClickListener(this)
    }

    override fun convert(holder: BaseViewHolder, item: ArticleBean) {
        holder.setText(R.id.tvContent, item.title)
        holder.setText(
            R.id.tvAuthor,
            if (TextUtils.isEmpty(item.author)) item.shareUser else item.author
        )
        holder.setText(
            R.id.tvChapter,
            String.format("%s·%s", item.superChapterName, item.chapterName)
        )
        holder.setText(R.id.tvTime, item.niceDate)
        holder.setVisible(R.id.top, holder.layoutPosition != 0)
        holder.getView<ShineButton>(R.id.ivCollect).apply {
            isChecked = item.collect ?: false
        }

    }

    override fun onItemChildClick(adapter: BaseQuickAdapter<*, *>, view: View, position: Int) {
        val item = data[position]
        when (view.id) {
            R.id.vItem -> {
                ARouterUtil.jumpWeb(item.link ?: "")
            }
            R.id.ivCollect -> {
                onArticleCollect?.onCollect(item)
                item.collect = !(item.collect ?: false)
            }
        }
    }

    private var onArticleCollect: OnArticleCollect? = null

    fun setOnArticleCollect(onArticleCollect: OnArticleCollect) {
        this.onArticleCollect = onArticleCollect
    }

    interface OnArticleCollect {
        fun onCollect(item: ArticleBean)
    }


}