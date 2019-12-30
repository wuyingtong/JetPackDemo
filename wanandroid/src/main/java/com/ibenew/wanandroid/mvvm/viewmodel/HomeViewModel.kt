package com.ibenew.wanandroid.mvvm.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.Transformations.map
import androidx.lifecycle.Transformations.switchMap
import androidx.lifecycle.ViewModel
import com.ibenew.wanandroid.mvvm.models.data.Banner
import com.ibenew.wanandroid.mvvm.models.data.BaseResult
import com.ibenew.wanandroid.mvvm.models.repository.HomeRepository

/**
 * Create by wuyt on 2019/12/18 12:14
 * {@link }
 */
class HomeViewModel(private val repository: HomeRepository) : ViewModel() {

    private val refreshTrigger = MutableLiveData<Boolean>()
    private val bannerList: LiveData<BaseResult<List<Banner>>> =
        switchMap(refreshTrigger)
        {
            repository.getBanners()
        }
//    private val articleList: LiveData<BaseResult<BaseListResult<Article>>> =
//        Transformations.switchMap(refreshTrigger)
//        {
//            repository.getArticles(1)
//        }

    val banners: LiveData<List<String>> = Transformations.map(bannerList) {
        val imagePaths = mutableListOf<String>()
        if (it.isSuccess() && !it.data.isNullOrEmpty()) {
            it.data.forEach { banner -> imagePaths.add(banner.imagePath) }
        }

        imagePaths
    }
//    val articles: LiveData<List<Article>> = Transformations.map(articleList) {
//        if (it.isSuccess() && !it.data.datas.isNullOrEmpty()) {
//            viewModelScope.launch {
//                repository.insertArticles(it.data.datas)
//            }
//            it.data.datas
//        } else {
//            null
//        }
//    }

    fun loadData(pullToRefresh: Boolean = true) {
        refreshTrigger.value = pullToRefresh
    }

    private val repoResult = map(refreshTrigger){
        repository.getArticleList()
    }
    val posts = switchMap(repoResult) { it.pagedList }
    val networkState = switchMap(repoResult) { it.networkState }
    val refreshState = switchMap(repoResult) { it.refreshState }

    fun refresh() {
        repoResult.value?.refresh?.invoke()
    }


    fun retry() {
        val listing = repoResult.value
        listing?.retry?.invoke()
    }

}