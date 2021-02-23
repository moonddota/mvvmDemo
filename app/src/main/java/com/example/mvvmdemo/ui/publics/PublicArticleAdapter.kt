package com.example.mvvmdemo.ui.publics

import android.text.TextUtils
import android.view.View
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.example.mvvmdemo.R
import com.example.mvvmdemo.bean.ArticleBean
import com.example.mvvmdemo.util.ARouterUtil
import com.example.mvvmdemo.widget.shinebutton.ShineButton
import java.lang.String

class PublicArticleAdapter :
    BaseQuickAdapter<ArticleBean, BaseViewHolder>(R.layout.rv_item_public_article) {

    private var hasTop = false
    fun setHasTop(hasTop: Boolean) {
        this.hasTop = hasTop
    }

    override fun convert(holder: BaseViewHolder, item: ArticleBean) {
        holder.setText(R.id.tvContent, item.title ?: "")
        holder.setText(
            R.id.tvAuthor,
            if (TextUtils.isEmpty(item.author ?: "")) item.shareUser ?: "" else item.author ?: ""
        )
        holder.setText(
            R.id.tvChapter,
            String.format(
                "%s·%s",
                item.superChapterName ?: "",
                item.chapterName ?: ""
            )
        )
        holder.setText(R.id.tvTime, item.niceDate ?: "")
        holder.setVisible(R.id.tvRefresh, item.fresh ?: false)
        holder.getView<ShineButton>(R.id.ivCollect).apply {
            isChecked = item.collect ?: false
            setOnClickListener {
                if (onArticleCollect != null) {
                    onArticleCollect!!.onCollect(item)
                }
                item.collect = !(item.collect ?: false)
                notifyItemChanged(holder.layoutPosition)
            }
        }

        holder.setGone(R.id.top, !(holder.layoutPosition == 0 && hasTop))

        holder.getView<View>(R.id.vItem).setOnClickListener {
            ARouterUtil.jumpWeb(item.link ?: "")
        }
    }

    private var onArticleCollect: OnArticleCollect? = null

    fun setOnArticleCollect(onArticleCollect: OnArticleCollect) {
        this.onArticleCollect = onArticleCollect
    }

    interface OnArticleCollect {
        fun onCollect(articleBean: ArticleBean)
    }

}