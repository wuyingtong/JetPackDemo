package com.ibenew.wanandroid.mvvm.view.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.ToastUtils
import com.ibenew.wanandroid.databinding.FragmentLoginBinding

/**
 * Create by wuyt on 2020/1/3 14:07
 * {@link }
 */
class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding
    private val loginViewModel: LoginViewModel by lazy {
        ViewModelProviders.of(this).get(LoginViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
            .apply {
                viewModel = loginViewModel
                lifecycleOwner = viewLifecycleOwner

                btnLoginStart.setOnClickListener { login() }
            }

        return binding.root
    }

    private fun login() {
        loginViewModel.userType.observe(this, Observer {
            LogUtils.d("userType：${it?.type}")
            if (it?.type.isNullOrEmpty()) {
                ToastUtils.showShort("账户不存在或类型为空!")
            } else {
                ToastUtils.showShort(String.format("账户类型:%s不存在!", it?.type))
            }
        })

        loginViewModel.token.observe(this, Observer {
            LogUtils.d("token：${it.data?.token}")
        })
    }
}