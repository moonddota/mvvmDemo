package com.example.mvvmdemo.ui.project

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupWindow
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.example.mvvmdemo.R
import com.example.mvvmdemo.bean.ProjectListRes


class ProjectPopuWindow(private val activity: Activity) {

    private lateinit var mAdapter: BaseQuickAdapter<ProjectListRes, BaseViewHolder>

    private val popWnd by lazy { PopupWindow(activity) }

    init {
        val view = LayoutInflater.from(activity).inflate(R.layout.project_popu, null)
        popWnd.contentView = view
        popWnd.width = ViewGroup.LayoutParams.MATCH_PARENT
        popWnd.height = ViewGroup.LayoutParams.WRAP_CONTENT
        popWnd.isOutsideTouchable = true
        popWnd.isFocusable = true  // 设置此参数获得焦点，否则无法点击
        popWnd.animationStyle = R.style.popmenu_animation
        popWnd.setOnDismissListener {
            backgroundAlpha(1f)
        }
        initAdapter(view)
    }

    private fun initAdapter(v: View) {
        mAdapter =
            object : BaseQuickAdapter<ProjectListRes, BaseViewHolder>(R.layout.project_popu_item) {
                override fun convert(holder: BaseViewHolder, item: ProjectListRes) {
                    holder.setVisible(R.id.line, holder.layoutPosition != data.size - 1)
                        .setText(R.id.tv, item.name)
                }
            }
        mAdapter.setOnItemClickListener { _, _, position ->
            popuClick?.onItemClick(mAdapter.data[position])
            popWnd.dismiss()
        }
        val rc = v.findViewById<RecyclerView>(R.id.rc)
        rc.layoutManager = LinearLayoutManager(activity)
        rc.adapter = mAdapter
    }


    fun show(view: View, list: List<ProjectListRes>) {
        backgroundAlpha(0.3f)
        popWnd.showAsDropDown(view)
        mAdapter.setList(list)
    }

    fun dismiss() {
        popWnd.dismiss()
    }

    private fun backgroundAlpha(bgAlpha: Float) {
//        val lp: WindowManager.LayoutParams = activity.window.attributes
//        lp.alpha = bgAlpha //0.0-1.0
//        activity.window.attributes = lp
    }

    fun isShow(): Boolean {
        return popWnd.isShowing
    }

    interface PopuClick {
        fun onItemClick(item: ProjectListRes)
    }

    private var popuClick: PopuClick? = null

    fun setOnClick(click: PopuClick) {
        popuClick = click
    }
}