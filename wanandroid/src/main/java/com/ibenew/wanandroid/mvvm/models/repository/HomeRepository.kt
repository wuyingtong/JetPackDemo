package com.ibenew.wanandroid.mvvm.models.repository

import androidx.annotation.MainThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.paging.Config
import androidx.paging.LivePagedListBuilder
import androidx.paging.toLiveData
import com.blankj.utilcode.util.LogUtils
import com.ibenew.wanandroid.http.RetrofitClient
import com.ibenew.wanandroid.http.WanApi
import com.ibenew.wanandroid.mvvm.models.data.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Create by wuyt on 2019/12/18 12:12
 * {@link }
 */
class HomeRepository(private val articleDao: ArticleDao) {

    private val api = RetrofitClient.getInstance().create(WanApi::class.java)


    fun getBanners(): LiveData<List<String>> {
        val banner = MutableLiveData<List<String>>()
        api.getBanners().enqueue(object : Callback<BaseResult<List<Banner>>> {
            override fun onFailure(call: Call<BaseResult<List<Banner>>>, t: Throwable) {
                LogUtils.d("${t.message}")
            }

            override fun onResponse(
                call: Call<BaseResult<List<Banner>>>,
                response: Response<BaseResult<List<Banner>>>
            ) {
                if (response.isSuccessful && !response.body()?.data.isNullOrEmpty()) {
                    val data = response.body()!!.data
                    val imagePaths = mutableListOf<String>()
                    data.forEach { imagePaths.add(it.imagePath) }

                    banner.value = imagePaths
                }
            }
        })

        return banner
    }

//    fun getArticles(page: Int): LiveData<BaseResult<BaseListResult<Article>>> =
//        api.getArticles(page)

    fun getArticleDataSource() = articleDao.getAllArticles().toLiveData(Config(pageSize = 5))

    //DB
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

    //network
    @MainThread
    fun getArticleFromNet(): Listing<Article> {
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