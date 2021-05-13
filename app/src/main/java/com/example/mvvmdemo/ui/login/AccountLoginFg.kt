package com.example.mvvmdemo.ui.login

import android.os.Handler
import android.text.TextUtils
import androidx.navigation.fragment.NavHostFragment
import com.example.mvvmdemo.R
import com.example.mvvmdemo.base.BaseViewModelFragment
import com.example.mvvmdemo.databinding.FgAccountLoginBinding
import com.example.mvvmdemo.ui.mine.setting.PopUtil

class AccountLoginFg : BaseViewModelFragment<AccountLoginVM, FgAccountLoginBinding>() {

    override fun providerVMClass(): Class<AccountLoginVM> = AccountLoginVM::class.java

    override fun initView() {

        binding.ivCha.setOnClickListener { requireActivity().finish() }
        binding.btnLogin.setOnClickListener { login() }
        binding.tvRegister.setOnClickListener {
            NavHostFragment.findNavController(this).navigate(R.id.action_fragment_register)
        }

    }

    override fun initData() {
        viewModel.data.observe(requireActivity(), {
            if (it) {
                requireActivity().finish()
            } else {
                binding.btnLogin.reset()
            }
        })
    }

    private fun login() {
        val username: String = binding.etAccount.text.toString().trim()
        if (TextUtils.isEmpty(username)) {
            PopUtil.show("请输入账号")
            return
        }
        val password: String = binding.etPassword.text.toString().trim { it <= ' ' }
        if (TextUtils.isEmpty(password)) {
            PopUtil.show("请输入密码")
            return
        }

        binding.btnLogin.startAnim()
        Handler().postDelayed({
            viewModel.login(username, password)
        }, 2000)
    }


}