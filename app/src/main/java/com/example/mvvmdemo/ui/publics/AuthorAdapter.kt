package com.example.mvvmdemo.ui.publics

import android.widget.ImageView
import androidx.core.content.ContextCompat
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.example.mvvmdemo.R
import com.example.mvvmdemo.bean.PublicAuthorListRes
import com.example.mvvmdemo.util.ext.asColor
import com.example.mvvmdemo.widget.hivelayoutmanager.HiveDrawable
import com.example.mvvmdemo.widget.hivelayoutmanager.HiveLayoutManager

class AuthorAdapter :
    BaseQuickAdapter<PublicAuthorListRes, BaseViewHolder>(R.layout.rv_item_author) {
    override fun convert(holder: BaseViewHolder, item: PublicAuthorListRes) {

        holder.setText(R.id.text, item.name ?: "")
            .getView<ImageView>(R.id.image).apply {
                val hiveDrawable = HiveDrawable(
                    HiveLayoutManager.VERTICAL,
                    R.color.main_text.asColor()
                )
                setImageDrawable(hiveDrawable)
            }


    }


}