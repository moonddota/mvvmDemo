package com.example.mvvmdemo.dialog

import android.view.Gravity
import android.view.ViewGroup
import com.example.mvvmdemo.R
import com.example.mvvmdemo.base.BaseDialogFragment
import com.example.mvvmdemo.databinding.MainDialogBinding

class TwoButtonDialog(
    var msg: String,
    var leftS: String,
    var rightS: String,
    var confirm :Confirm
) :
    BaseDialogFragment<MainDialogBinding>() {


    override fun initView() {
        binding.tvMsg.text = msg
        with(binding.tvCancel) {
            text = leftS
            setOnClickListener { this@TwoButtonDialog.dismiss() }
        }

        with(binding.tvConfirm) {
            text = rightS
            setOnClickListener { confirm.onConfirm(this@TwoButtonDialog) }
        }

    }

    override fun initWindow() = Triple(true, Gravity.CENTER, R.style.dialogAction_animation)

    override fun initLayout(): Pair<Int, Int> {
        return Pair(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
    }

    interface Confirm {
        fun onConfirm(dialog: TwoButtonDialog)
    }



}