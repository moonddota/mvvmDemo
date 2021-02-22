package com.example.mvvmdemo.ui.square

import android.text.TextUtils
import androidx.appcompat.widget.AppCompatImageView
import com.blankj.utilcode.util.ConvertUtils
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.example.mvvmdemo.R
import com.example.mvvmdemo.bean.ArticleBean
import com.example.mvvmdemo.constant.C
import com.example.mvvmdemo.widget.shinebutton.ShineButton

class RvAdapterArticleList : BaseMultiItemQuickAdapter<ArticleBean, BaseViewHolder>() {

    private var hasTop = false
    fun setHasTop(hasTop: Boolean) {
        this.hasTop = hasTop
    }

    private val glide by lazy { Glide.with(context) }
    private val options by lazy {
        RequestOptions.bitmapTransform(
            RoundedCorners(
                ConvertUtils.dp2px(
                    5f
                )
            )
        )
    }


    init {
        addItemType(C.ARTICLE_ITEM_TEXT, R.layout.rv_item_article_text)
        addItemType(C.ARTICLE_ITEM_TEXT_PIC, R.layout.rv_item_article_text_pic)
        addChildClickViewIds(R.id.ivCollect)
    }

    override fun convert(holder: BaseViewHolder, item: ArticleBean) {
        val superChapterName = item.superChapterName ?: ""
        val chapterName = item.chapterName ?: ""
        when (holder.itemViewType) {
            C.ARTICLE_ITEM_TEXT -> {
                holder.setText(
                    R.id.tvChapter,
                    if (TextUtils.isEmpty(superChapterName)) chapterName else String.format(
                        "%s·%s",
                        superChapterName,
                        chapterName
                    )
                )
                    .setText(R.id.tvTime, item.niceDate)
                    .setGone(R.id.tvRefresh, !(item.fresh ?: false))
            }

            C.ARTICLE_ITEM_TEXT_PIC -> {
                holder.setText(R.id.tvContent, item.desc ?: "")
                    .getView<AppCompatImageView>(R.id.image).apply {
                        glide.load(item.envelopePic ?: "").into(this)
                    }

            }
        }
        holder.setText(R.id.tvTitle, item.title ?: "")
            .setText(
                R.id.tvAuthor,
                if (TextUtils.isEmpty(item.author ?: "")) item.shareUser ?: "" else item.author
                    ?: ""
            )
            .setGone(R.id.top, !(hasTop && holder.adapterPosition == 0))
            .getView<ShineButton>(R.id.ivCollect).apply {
            isChecked = item.collect ?: false
        }

    }


    /**
     * 取消收藏，做单个删除
     */
    fun cancelCollect(position: Int) {
        data.removeAt(position)
        notifyItemRemoved(position)
    }
}