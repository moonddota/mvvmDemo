package com.example.mvvmdemo.base

import android.app.Dialog
import android.os.Build
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.annotation.Nullable
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import androidx.viewbinding.ViewBinding
import com.dylanc.viewbinding.inflateBindingWithGeneric
import com.example.mvvmdemo.R

abstract class BaseDialogFragment<vb : ViewBinding> : DialogFragment() {
    private var _binding: vb? = null
    val binding: vb get() = _binding!!

    override fun onActivityCreated(@Nullable savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if (dialog != null) {
            try {
                // 解决Dialog内D存泄漏
                dialog!!.setOnDismissListener(null)
                dialog!!.setOnCancelListener(null)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    override fun onStart() {
        super.onStart()
        // 全屏显示Dialog，重新测绘宽高
        if (dialog != null) {
            val dm = DisplayMetrics()
            requireActivity().windowManager.defaultDisplay.getMetrics(dm)
            dialog!!.window!!.setLayout(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        @Nullable container: ViewGroup?,
        @Nullable savedInstanceState: Bundle?
    ): View? {
        _binding = inflateBindingWithGeneric(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    /**
     * 初始化界面
     */
    abstract fun initView()
    abstract fun setCanceled(): Boolean

    override fun show(fm: FragmentManager, tag: String?) {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.JELLY_BEAN) {
            if (fm.isDestroyed)
                return
        }
        fm.beginTransaction().remove(this).commit();
        super.show(fm, tag)
    }

    /**
     * 全屏显示Dialog
     *
     * @param savedInstanceState
     * @return
     */
    override fun onCreateDialog(@Nullable savedInstanceState: Bundle?): Dialog {
        val dialog = Dialog(requireActivity())
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.window!!.setBackgroundDrawableResource(R.color.white)
        dialog.setCanceledOnTouchOutside(setCanceled())
        dialog.setCancelable(setCanceled())
        return dialog
    }
}