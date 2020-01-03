package com.ibenew.wanandroid.mvvm.models.repository

import androidx.annotation.MainThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.paging.Config
import androidx.paging.toLiveData
import com.ibenew.wanandroid.http.*
import com.ibenew.wanandroid.mvvm.models.data.Article
import com.ibenew.wanandroid.mvvm.models.data.Banner
import com.ibenew.wanandroid.mvvm.models.data.Listing
import com.ibenew.wanandroid.mvvm.models.data.Resource
import com.ibenew.wanandroid.mvvm.models.db.ArticleDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Create by wuyt on 2019/12/18 12:12
 * {@link }
 */
class HomeRepository(private val articleDao: ArticleDao) {

    private val api = RetrofitClient.getInstance().create(WanApi::class.java)

    fun getBanner(): LiveData<Resource<List<Banner>>> =
        object : NetworkBoundResource<List<Banner>>() {
            override fun createCall(): LiveData<BaseResponse<List<Banner>>> {
                return api.getBanners()
            }
        }.asLiveData()

    fun getArticle(page: Int): LiveData<Resource<BaseListResponse<Article>>> =
        object : NetworkBoundResource<BaseListResponse<Article>>() {
            override fun createCall(): LiveData<BaseResponse<BaseListResponse<Article>>> {
                return api.getArticles(page)
            }
        }.asLiveData()

    //network
    @MainThread
    fun getArticleFromPaging(): Listing<Article> {
        val sourceFactory = ArticleDataSourceFactory(api)
        // We use toLiveData Kotlin ext. function here, you could also use LivePagedListBuilder
        val livePagedList = sourceFactory.toLiveData(
            // we use Config Kotlin ext. function here, could also use PagedList.Config.Builder
            config = Config(
                pageSize = 10,
                enablePlaceholders = false,
                initialLoadSizeHint = 10 * 2
            )
        )

        val refreshState = Transformations.switchMap(sourceFactory.sourceLiveData) {
            it.initialLoad
        }
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