package com.example.mvvmdemo.ui.login

import android.os.Handler
import android.text.TextUtils
import androidx.navigation.fragment.NavHostFragment
import com.example.mvvmdemo.base.BaseViewModelFragment
import com.example.mvvmdemo.databinding.FgAccountRegisterBinding
import com.example.mvvmdemo.ui.mine.setting.PopUtil

class AccountRegisterFg :
    BaseViewModelFragment<AccountRegisterVM, FgAccountRegisterBinding>() {


    override fun providerVMClass(): Class<AccountRegisterVM> = AccountRegisterVM::class.java

    override fun initView() {

        binding.btnLogin.setText("注册")
        binding.btnLogin.setOnClickListener { register() }
        binding.ivCha.setOnClickListener {
            NavHostFragment.findNavController(this).navigateUp()
        }
    }

    override fun initData() {

    }

    override fun onStop() {
        super.onStop()
        binding.btnLogin.reset()
        viewModel.data.observe(viewLifecycleOwner,{
            if (it){
                requireActivity().finish()
            }else{
                binding.btnLogin.success()
            }
        })
    }


    private fun register(){
        val username: String =binding. etAccount.text.toString().trim()
        if (TextUtils.isEmpty(username)) {
            PopUtil.show("请输入账号")
            return
        }
        val password: String = binding.etPassword.text.toString().trim { it <= ' ' }
        if (TextUtils.isEmpty(password)) {
            PopUtil.show("请输入密码")
            return
        }
        binding.  btnLogin.startAnim()
        Handler().postDelayed({
            viewModel.register(username, password, password)
        }, 2000)
    }

}