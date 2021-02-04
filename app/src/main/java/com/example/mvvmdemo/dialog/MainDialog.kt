package com.example.mvvmdemo.dialog

import android.view.Gravity
import androidx.fragment.app.FragmentManager
import com.example.mvvmdemo.R
import com.example.mvvmdemo.base.BaseDialogFragment
import com.example.mvvmdemo.databinding.MainDialogBinding

class MainDialog(var fm: FragmentManager,var tagger :String) :
    BaseDialogFragment<MainDialogBinding>() {

    private var num = 0
    override fun initView() {
        binding.tv.text = "qbc$num"
    }

    override fun initWindow() = Triple(true, Gravity.CENTER, R.style.dialogAction_animation)


    fun shwoDialog(num :Int) {
        show(fm, "MainDialog")
        this.num = num
    }

}