package com.example.mvvmdemo.dialog

import com.example.mvvmdemo.databinding.MainDialogBinding
import com.example.mvvmdemo.base.BaseDialogFragment

class MainDialog: BaseDialogFragment<MainDialogBinding>() {


    override fun initView() {
        binding.tv.text = "山東黃金卡仕達卡仕達卡仕達庫哈斯肯定會"
    }

    override fun setCanceled(): Boolean  = true


}