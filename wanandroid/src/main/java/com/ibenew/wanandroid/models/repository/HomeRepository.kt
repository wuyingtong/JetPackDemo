package com.ibenew.wanandroid.models.repository

import androidx.lifecycle.LiveData
import com.ibenew.wanandroid.app.http.RetrofitClient
import com.ibenew.wanandroid.app.http.WanService
import com.ibenew.wanandroid.models.data.Banner
import com.ibenew.wanandroid.models.data.BaseResult

/**
 * Create by wuyt on 2019/12/18 12:12
 * {@link }
 */
class HomeRepository {
    init {
        wanService = RetrofitClient.getInstance().create(WanService::class.java)
    }

    fun getBanners(): LiveData<BaseResult<List<Banner>>> = wanService.getBanners()

    companion object {

        // For Singleton instantiation
        @Volatile
        private var instance: HomeRepository? = null

        private lateinit var wanService: WanService

        fun getInstance() =
            instance ?: synchronized(this) {
                instance ?: HomeRepository().also { instance = it }
            }
    }
}