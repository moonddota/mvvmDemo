package com.example.mvvmdemo.ui.project

import android.text.TextUtils
import android.view.View
import android.widget.ImageView
import androidx.core.view.isGone
import androidx.core.view.isVisible
import com.blankj.utilcode.util.ConvertUtils.dp2px
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.listener.OnItemChildClickListener
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.example.mvvmdemo.App
import com.example.mvvmdemo.R
import com.example.mvvmdemo.bean.ArticleBean
import com.example.mvvmdemo.util.ARouterUtil
import com.example.mvvmdemo.widget.shinebutton.ShineButton
import java.lang.String

class ProgectAdapter : BaseQuickAdapter<ArticleBean, BaseViewHolder>(R.layout.rv_item_progect),
    OnItemChildClickListener {

    private val glide by lazy { Glide.with(App.instance) }
    private val options by lazy { RequestOptions.bitmapTransform(RoundedCorners(dp2px(5f))) }

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
        holder.setGone(R.id.tvRefresh, !(item.fresh ?: false))
        holder.getView<ShineButton>(R.id.ivCollect).apply {
            isChecked = item.collect ?: false
        }

        holder.getView<ImageView>(R.id.image).apply {
            if (TextUtils.isEmpty(item.envelopePic)) {
                isGone = true
            } else {
                isVisible = true
                glide.load(item.envelopePic)
                    .apply(options)
                    .into(this)
            }
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