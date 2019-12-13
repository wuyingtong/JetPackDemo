package com.ibenew.jetpack.viewmodel

import android.app.Application
import android.content.Context
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.ibenew.jetpack.app.http.ApiService
import okhttp3.OkHttpClient
import retrofit2.Retrofit

/**
 * Create by wuyt on 2019/12/11 9:41
 * {@link }
 */
class LoginViewModel(application: Application) : AndroidViewModel(application) {

    private var mApiService: ApiService = Retrofit.Builder()
        .baseUrl("https://www.wanandroid.com/")
        .client(OkHttpClient())
        .build()
        .create(ApiService::class.java)

    private val mContext: Context = application

    val mUserName: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    val mPassword: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    fun login(){

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