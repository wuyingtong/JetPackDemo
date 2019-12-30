package com.ibenew.wanandroid.mvvm.models.data

/**
 * Create by wuyt on 2019/12/18 16:19
 * {@link }
 */
data class BaseResult<T>(val data: T,val errorMsg: String, val errorCode: Int) {
    fun isSuccess() = errorCode == 0
}