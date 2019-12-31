package com.ibenew.wanandroid.mvvm.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations.map
import androidx.lifecycle.Transformations.switchMap
import androidx.lifecycle.ViewModel
import com.ibenew.wanandroid.mvvm.models.repository.HomeRepository

/**
 * Create by wuyt on 2019/12/18 12:14
 * {@link }
 */
class HomeViewModel(private val repository: HomeRepository) : ViewModel() {

    private val refreshTrigger = MutableLiveData<Boolean>()
//    private val bannerList: LiveData<BaseResult<List<Banner>>> =
//        switchMap(refreshTrigger)
//        {
//            repository.getBanners()
//        }
//    private val articleList: LiveData<BaseResult<BaseListResult<Article>>> =
//        switchMap(refreshTrigger)
//        {
//            repository.getArticles(1)
//        }

    val articleDataSource = switchMap(refreshTrigger) {
        repository.getArticleDataSource()
    }

    val banners: LiveData<List<String>> = repository.getBanners()
//    val articles: LiveData<List<Article>> = map(articleList) {
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

    private val repoArticle = map(refreshTrigger) { repository.getArticleFromNet() }
    val repoResult = switchMap(repoArticle) { it.pagedList }
    val networkState = switchMap(repoArticle) { it.networkState }
    val refreshState = switchMap(repoArticle) { it.refreshState }

    fun refresh() {
        repoArticle.value?.refresh?.invoke()
    }

    fun retry() = repoArticle.value?.retry?.invoke()


}