package com.ibenew.wanandroid.mvvm.view.login

import androidx.lifecycle.*
import com.ibenew.wanandroid.http.LoginApi
import com.ibenew.wanandroid.http.RetrofitClient

/**
 * Create by wuyt on 2020/1/3 14:13
 * {@link }
 */
class LoginViewModel : ViewModel() {

    private val api by lazy { RetrofitClient.getInstance().create(LoginApi::class.java) }

    val username = MutableLiveData<String>("SA100806")
    val userpwd = MutableLiveData<String>("2345678")
    val loginSuccess: LiveData<Boolean> = MutableLiveData()

    fun verify(): Boolean {
        return !username.value.isNullOrEmpty() && !userpwd.value.isNullOrEmpty()
    }

    val userType = Transformations.switchMap(username) {
        api.getUserType(it).map { response ->
            response.data
        }
    }
    val token = Transformations.switchMap(userType) { api.token() }

    private fun login(){
        api.getUserType(username.value!!)
            .map {

            }
    }

}