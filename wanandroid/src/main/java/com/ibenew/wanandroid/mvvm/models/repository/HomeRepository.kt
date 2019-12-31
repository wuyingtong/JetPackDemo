package com.ibenew.wanandroid.mvvm.models.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.paging.Config
import androidx.paging.LivePagedListBuilder
import androidx.paging.toLiveData
import com.ibenew.wanandroid.http.RetrofitClient
import com.ibenew.wanandroid.http.WanApi
import com.ibenew.wanandroid.mvvm.models.data.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Create by wuyt on 2019/12/18 12:12
 * {@link }
 */
class HomeRepository(private val articleDao: ArticleDao) {

    val api = RetrofitClient.getInstance().create(WanApi::class.java)


    fun getBanners(): LiveData<BaseResult<List<Banner>>> = api.getBanners()

    fun getArticles(page: Int): LiveData<BaseResult<BaseListResult<Article>>> =
        api.getArticles(page)

    fun getArticleDataSource() = articleDao.getAllArticles().toLiveData(Config(pageSize = 5))

    fun getArticleList(): Listing<Article> {
        val sourceFactory = ArticleDataSourceFactory(api)
        val livePagedList = LivePagedListBuilder<Int, Article>(
            sourceFactory, Config(pageSize = 1, initialLoadSizeHint = 1 * 2)
        ).build()
        val refreshState =
            Transformations.switchMap(sourceFactory.sourceLiveData) { it.initialLoad }
        return Listing(
            pagedList = livePagedList,
            networkState = Transformations.switchMap(sourceFactory.sourceLiveData) {
                it.networkState
            },
            retry = {
                sourceFactory.sourceLiveData.value?.retryAllFailed()
            },
            refresh = {
                sourceFactory.sourceLiveData.value?.invalidate()
            },
            refreshState = refreshState
        )
    }

    suspend fun insertArticles(articles: List<Article>) {
        withContext(Dispatchers.IO) {
            articleDao.insertAll(articles)
        }
    }

    companion object {

        // For Singleton instantiation
        @Volatile
        private var instance: HomeRepository? = null

        fun getInstance(articleDao: ArticleDao) =
            instance ?: synchronized(this) {
                instance ?: HomeRepository(articleDao).also { instance = it }
            }
    }
}