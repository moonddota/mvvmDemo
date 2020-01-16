package com.example.mvvmdemo.ui.login

import android.content.Context
import android.content.Intent
import android.view.View
import androidx.lifecycle.Observer
import com.blankj.utilcode.util.LogUtils
import com.example.mvvmdemo.R
import com.example.mvvmdemo.base.BaseViewModelActivity
import com.example.mvvmdemo.data.loginData
import com.jeremyliao.liveeventbus.LiveEventBus
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.coroutines.*
import kotlin.system.measureTimeMillis


class LoginActivity : BaseViewModelActivity<LoginViewModel>() {

    companion object {

        fun getLoginActivity(context: Context): Intent {
            val intent = Intent(context, LoginActivity::class.java)
            return intent
        }
    }


    private lateinit var data: loginData

    override fun getLayoutId(): Int = R.layout.activity_login

    override fun providerVMClass(): Class<LoginViewModel>? = LoginViewModel::class.java

    override fun initView() {

        topbar.addLeftBackImageButton().setOnClickListener(View.OnClickListener {
            finish()
        })
        topbar.setBackgroundColor(resources.getColor(R.color.colorAccent))

        login.setOnClickListener {
            val name = username.text.toString()
            val pwd = password.text.toString()
            viewModel.loginDatas(name, pwd)

            LiveEventBus
                .get("key_name")
                .post("dsadsadsadsadsadsadsadsadsadsadsadsadsadsadsadsadsa");
        }


    }

    override fun initData() {
        viewModel.getLogin().observe(this, Observer {
            if (it.code == 0) {
                toast("登录成功")

            } else {
                toast("账号或密码错误")
            }
        })

        test()

    }


    fun test() {
        GlobalScope.launch {
            /*measureTimeMillis返回给定的block代码的执行时间*/
            val time = measureTimeMillis {
                val sum = withContext(Dispatchers.IO) {
                    //                    val one = async { one() }
//                    val two = async { two() }
//                    one.await() + two.await()
                    val one = one()
                    val two = two()
                    one + two
                }
                println("两个方法返回值的和：${sum}")
            }
            println("执行耗时：${time}")
        }
    }

    suspend fun one(): Int {
        delay(1500)
        return 1
    }

    suspend fun two(): Int {
        delay(1500)
        return 2
    }

}