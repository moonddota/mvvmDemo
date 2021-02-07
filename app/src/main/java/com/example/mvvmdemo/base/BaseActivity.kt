package com.example.mvvmdemo.base

import android.content.pm.ActivityInfo
import android.os.Build
import android.os.Bundle
import android.os.IBinder
import android.view.MotionEvent
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import com.dylanc.viewbinding.inflateBindingWithGeneric
import com.example.mvvmdemo.R
import com.example.mvvmdemo.widget.slideback.SlideBack
import com.gyf.immersionbar.BarHide
import com.gyf.immersionbar.ImmersionBar

abstract class BaseActivity<vb : ViewBinding> : AppCompatActivity() {
    lateinit var binding: vb

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        beforeLayout()
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT //竖屏
        setSuspension()
        binding = inflateBindingWithGeneric(layoutInflater)
        setContentView(binding.root)
        initImmersionBar()
        initView()
        initData()
        if (canSwipeBack()) {
            //开启滑动返回
            SlideBack.create()
                .attachToActivity(this)
        }
    }

    open fun beforeLayout() {}
    abstract fun initView()
    abstract fun initData()

    protected open fun fullScreen(): Boolean {
        return false
    }

    protected open fun canSwipeBack(): Boolean {
        return true
    }

    private fun initImmersionBar() {
        if (!fullScreen()) {
            ImmersionBar.with(this)
                .statusBarView(R.id.statusBarView)
                .statusBarDarkFont(true)
                .transparentBar()
                .keyboardEnable(true)
                .hideBar(BarHide.FLAG_HIDE_NAVIGATION_BAR)
                .init()
        } else {
            ImmersionBar.with(this)
                .fullScreen(true)
                .keyboardEnable(true)
                .hideBar(BarHide.FLAG_HIDE_BAR)
                .init()
        }
    }

    /**
     * 悬浮窗设置
     */
    protected open fun setSuspension() {
        val mParams = window.attributes
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            mParams.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
        } else {
            //xxxx为你原来给低版本设置的Type
            mParams.type = WindowManager.LayoutParams.TYPE_SYSTEM_ALERT
        }
    }

    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        if (touchHideSoft()) {
            if (ev.action == MotionEvent.ACTION_DOWN) {
                val v = currentFocus
                if (isShouldHideKeyboard(v, ev)) {
                    hideKeyboard(v!!.windowToken)
                }
            }
        }
        return super.dispatchTouchEvent(ev)
    }

    /**
     * 是否触摸edittext以外的隐藏软键盘
     *
     * @return
     */
    protected open fun touchHideSoft(): Boolean {
        return true
    }

    /**
     * 根据EditText所在坐标和用户点击的坐标相对比，来判断是否隐藏键盘，因为当用户点击EditText时则不能隐藏
     *
     * @param v
     * @param event
     * @return
     */
    open fun isShouldHideKeyboard(v: View?, event: MotionEvent): Boolean {
        if (v != null && v is EditText) {
            val l = intArrayOf(0, 0)
            v.getLocationInWindow(l)
            val left = l[0]
            val top = l[1]
            val bottom = top + v.getHeight()
            val right = left + v.getWidth()
            return !(event.x > left && event.x < right && event.y > top && event.y < bottom)
        }
        // 如果焦点不是EditText则忽略，这个发生在视图刚绘制完，第一个焦点不在EditText上，和用户用轨迹球选择其他的焦点
        return false
    }


    /**
     * 获取InputMethodManager，隐藏软键盘
     */
    open fun hideKeyboard(token: IBinder?) {
        if (token != null) {
            val im = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            im.hideSoftInputFromWindow(token, InputMethodManager.HIDE_NOT_ALWAYS)
        }
    }

}