package com.ibenew.wanandroid.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.ibenew.wanandroid.models.data.Banner
import com.ibenew.wanandroid.models.data.BaseResult
import com.ibenew.wanandroid.models.repository.HomeRepository

/**
 * Create by wuyt on 2019/12/18 12:14
 * {@link }
 */
class HomeViewModel : ViewModel() {

    private val mRepository by lazy { HomeRepository.getInstance() }

    private val refreshTrigger = MutableLiveData<Boolean>(true)
    private val bannerList: LiveData<BaseResult<List<Banner>>> =
        Transformations.switchMap(refreshTrigger)
        {
            mRepository.getBanners()
        }

    val banners: LiveData<List<String>> = Transformations.map(bannerList) {
        val imagePaths = mutableListOf<String>()
        if (it.isSuccess() && !it.data.isNullOrEmpty()) {
            it.data.forEach { banner -> imagePaths.add(banner.imagePath) }
        }

        imagePaths
    }
}