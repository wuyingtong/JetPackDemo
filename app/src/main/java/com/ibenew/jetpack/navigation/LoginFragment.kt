package com.ibenew.jetpack.navigation

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.ibenew.jetpack.databinding.FragmentLoginBinding
import com.ibenew.jetpack.viewmodel.LoginViewModel

/**
 * Create by wuyt on 2019/12/11 9:13
 * {@link }
 */
class LoginFragment : Fragment() {

    private lateinit var mLoinViewModel: LoginViewModel

    companion object {
        const val TAG = "LoginFragment"
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = FragmentLoginBinding.inflate(inflater,container,false)
//        val binding: FragmentLoginBinding = DataBindingUtil.inflate(
//            inflater,
//            R.layout.fragment_login,
//            container,
//            false
//        )

        mLoinViewModel = ViewModelProviders.of(this)[LoginViewModel::class.java]
        binding.vm = mLoinViewModel
        binding.lifecycleOwner = this
        mLoinViewModel.mUserName.observe(this, Observer {
            // 订阅可观察对象
            Log.d(TAG, it)
        })

        return binding.root
    }
}


