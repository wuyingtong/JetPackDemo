package com.ibenew.jetpack.model

/**
 * Create by wuyt on 2019/12/13 17:55
 * {@link }
 */
data class BaseResponse<out T>(
    val data: T,
    val errorCode: Int,
    val errorMsg: String
) {
    fun isSuccess() = errorCode == 0
}