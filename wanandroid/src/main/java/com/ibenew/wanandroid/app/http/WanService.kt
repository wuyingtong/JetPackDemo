package com.ibenew.wanandroid.app.http

import androidx.lifecycle.LiveData
import com.ibenew.wanandroid.models.data.Banner
import com.ibenew.wanandroid.models.data.BaseResult
import retrofit2.http.GET

/**
 * Create by wuyt on 2019/12/18 15:29
 * {@link }
 */
interface WanService {
    @GET("banner/json")
    fun getBanners():LiveData<BaseResult<List<Banner>>>
}