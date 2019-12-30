package com.ibenew.wanandroid.mvvm.models.data

/**
 * Create by wuyt on 2019/12/18 16:19
 * {@link }
 */
data class BaseListResult<T>(
    val datas: List<T>,
    val curPage: Int,
    val offset: Int,
    val over: Boolean,
    val pageCount: Int,
    val size: Int,
    val total: Int
)