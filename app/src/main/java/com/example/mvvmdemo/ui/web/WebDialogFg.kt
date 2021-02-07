package com.example.mvvmdemo.ui.web

import android.content.Intent
import android.net.Uri
import android.text.TextUtils
import android.view.Gravity
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import com.example.mvvmdemo.R
import com.example.mvvmdemo.base.BaseDialogFragment
import com.example.mvvmdemo.databinding.FgWebBinding

class WebDialogFg(var fm: FragmentManager) :BaseDialogFragment<FgWebBinding>() {

    private var url: String? = null

    override fun initWindow(): Triple<Boolean, Int, Int> {
        return   Triple(true, Gravity.BOTTOM, R.style.dialogAction_animation)
    }

    override fun initLayout(): Pair<Int, Int> {
        return Pair(-1,-2)
    }

    override fun initView() {
        binding.tvOpen.setOnClickListener {
            if (!TextUtils.isEmpty(url) && (url!!.startsWith("http") || url!!.startsWith("https"))) {
                val i = Intent(Intent.ACTION_VIEW)
                i.data = Uri.parse(url)
                startActivity(i)
            }
            dismiss()
        }
        binding.llCancel.setOnClickListener {
            dismiss()
        }
        binding.parent.setOnClickListener {
            dismiss()
        }
    }



    fun shwoDialog(url :String) {
        show(fm, "webDialog")
        this.url = url
    }

}