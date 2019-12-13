package com.ibenew.jetpack.app.http

import androidx.lifecycle.LiveData
import com.ibenew.jetpack.model.Banner
import com.ibenew.jetpack.model.BaseResponse
import retrofit2.http.GET

/**
 * Create by wuyt on 2019/12/13 17:43
 * {@link }
 */
interface ApiService {
    @GET("banner/json")
    suspend fun getBanner(): LiveData<BaseResponse<MutableList<Banner>>>
}