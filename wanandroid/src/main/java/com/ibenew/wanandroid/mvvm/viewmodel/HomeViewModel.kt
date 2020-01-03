package com.ibenew.wanandroid.mvvm.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations.map
import androidx.lifecycle.Transformations.switchMap
import androidx.lifecycle.ViewModel
import com.ibenew.wanandroid.http.BaseListResponse
import com.ibenew.wanandroid.mvvm.models.data.Article
import com.ibenew.wanandroid.mvvm.models.data.Banner
import com.ibenew.wanandroid.mvvm.models.data.Resource
import com.ibenew.wanandroid.mvvm.models.repository.HomeRepository

/**
 * Create by wuyt on 2019/12/18 12:14
 * {@link }
 */
class HomeViewModel(private val repository: HomeRepository) : ViewModel() {

    private val refreshTrigger = MutableLiveData<Boolean>()
    val banners: LiveData<Resource<List<Banner>>> =
        switchMap(refreshTrigger) { repository.getBanner() }

    val articles: LiveData<Resource<BaseListResponse<Article>>> =
        switchMap(refreshTrigger) { repository.getArticle(0) }

    private val repoArticle = map(refreshTrigger) { repository.getArticleFromPaging() }
    val repoResult = switchMap(repoArticle) { it.pagedList }
    val networkState = switchMap(repoArticle) { it.networkState }
    val refreshState = switchMap(repoArticle) { it.refreshState }

    fun refresh() {
        repoArticle.value?.refresh?.invoke()
        refreshTrigger.value = true
    }

    fun retry() = repoArticle.value?.retry?.invoke()

}