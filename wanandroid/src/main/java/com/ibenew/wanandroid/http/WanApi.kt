package com.ibenew.wanandroid.http

import com.ibenew.wanandroid.mvvm.models.data.Article
import com.ibenew.wanandroid.mvvm.models.data.Banner
import com.ibenew.wanandroid.mvvm.models.data.BaseListResult
import com.ibenew.wanandroid.mvvm.models.data.BaseResult
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * Create by wuyt on 2019/12/18 15:29
 * {@link }
 */
interface WanApi {
    @GET("banner/json")
    fun getBanners(): Call<BaseResult<List<Banner>>>

    @GET("article/list/{page}/json")
    fun getArticles(@Path("page") page: Int): Call<BaseResult<BaseListResult<Article>>>

}