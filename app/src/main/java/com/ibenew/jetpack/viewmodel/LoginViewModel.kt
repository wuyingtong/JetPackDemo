package com.ibenew.jetpack.viewmodel

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.*

/**
 * Create by wuyt on 2019/12/11 9:41
 * {@link }
 */
class LoginViewModel(application: Application) : AndroidViewModel(application) {

    val mUserName: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    val mPassword: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    fun login() {
        if (mUserName.value == "admin" && mPassword.value == "123") {
            Toast.makeText(getApplication(), "登录成功", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(
                getApplication(),
                "${mUserName.value}--${mPassword.value}",
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}