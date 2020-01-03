package com.ibenew.wanandroid.http

import com.google.gson.annotations.SerializedName

/**
 * Create by wuyt on 2020/1/2 18:07
 * {@link }
 */
data class BaseResponse<T>(
    val data: T?,
    @SerializedName("message") val errorMsg: String,
    @SerializedName("resultCode") val errorCode: Int,
    val status: String? = null
) {
    fun isSuccessful() = errorCode == 0
}

data class BaseListResponse<T>(
    val datas: List<T>?,
    val curPage: Int,
    val offset: Int,
    val over: Boolean,
    val pageCount: Int,
    val size: Int,
    val total: Int
)