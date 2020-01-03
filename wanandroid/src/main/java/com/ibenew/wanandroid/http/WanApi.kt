package com.ibenew.wanandroid.http

import androidx.lifecycle.LiveData
import com.ibenew.wanandroid.mvvm.models.data.Article
import com.ibenew.wanandroid.mvvm.models.data.Banner
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * Create by wuyt on 2019/12/18 15:29
 * {@link }
 */
interface WanApi {


    @GET("banner/json")
    fun getBanners(): LiveData<BaseResponse<List<Banner>>>

    @GET("article/list/{page}/json")
    fun getArticles(@Path("page") page: Int): LiveData<BaseResponse<BaseListResponse<Article>>>

}