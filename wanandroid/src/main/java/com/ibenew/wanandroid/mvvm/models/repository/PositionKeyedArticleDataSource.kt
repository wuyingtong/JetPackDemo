package com.ibenew.wanandroid.mvvm.models.repository

import androidx.lifecycle.MutableLiveData
import androidx.paging.PositionalDataSource
import com.ibenew.wanandroid.http.NetworkState
import com.ibenew.wanandroid.http.WanApi
import com.ibenew.wanandroid.mvvm.models.data.Article
import java.io.IOException
import java.util.concurrent.Executors

/**
 * Create by wuyt on 2019/12/30 15:22
 * {@link }
 * 适用于目标数据总数固定，通过特定的位置加载数据，
 * 这里Key是Integer类型的位置信息，T即Value。
 * 比如从数据库中的1200条开始加在20条数据。
 */
class PositionKeyedArticleDataSource(private val api: WanApi) : PositionalDataSource<Article>() {

    // keep a function reference for the retry event
    private var retry: (() -> Any)? = null

    /**
     * There is no sync on the state because paging will always call loadInitial first then wait
     * for it to return some success value before calling loadAfter.
     */
    val networkState = MutableLiveData<NetworkState>()

    val initialLoad = MutableLiveData<NetworkState>()

    fun retryAllFailed() {
        val prevRetry = retry
        retry = null
        prevRetry?.let {
            Executors.newFixedThreadPool(5).execute { it.invoke() }
        }
    }

    override fun loadInitial(params: LoadInitialParams, callback: LoadInitialCallback<Article>) {
        // update network states.
        // we also provide an initial load state to the listeners so that the UI can know when the
        // very first list is loaded.
        networkState.postValue(NetworkState.LOADING)
        initialLoad.postValue(NetworkState.LOADING)

        val request = api.getArticles(0)
        // triggered by a refresh, we better execute sync

        try {
//            request.enqueue(object : Callback<BaseResult<BaseListResult<Article>>> {
//                override fun onFailure(
//                    call: Call<BaseResult<BaseListResult<Article>>>,
//                    t: Throwable
//                ) {
//
//                }
//
//                override fun onResponse(
//                    call: Call<BaseResult<BaseListResult<Article>>>,
//                    response: Response<BaseResult<BaseListResult<Article>>>
//                ) {
//                    if (response.isSuccessful) {
//                        val items = response.body()?.data?.datas ?: emptyList()
//                        retry = null
//                        networkState.postValue(NetworkState.LOADED)
//                        initialLoad.postValue(NetworkState.LOADED)
//                        callback.onResult(items, items.size, response.body()?.data?.total ?: 0)
//                    }
//                }
//            })

        } catch (ioException: IOException) {
            retry = {
                loadInitial(params, callback)
            }
            val error = NetworkState.error(ioException.message ?: "unknown error")
            networkState.postValue(error)
            initialLoad.postValue(error)
        }
    }

    override fun loadRange(params: LoadRangeParams, callback: LoadRangeCallback<Article>) {
//        api.getArticles(params.startPosition).enqueue(
//            object : Callback<BaseResult<BaseListResult<Article>>> {
//                override fun onFailure(
//                    call: Call<BaseResult<BaseListResult<Article>>>,
//                    t: Throwable
//                ) {
//                    // keep a lambda for future retry
//                    retry = {
//                        loadRange(params, callback)
//                    }
//                    // publish the error
//                    networkState.postValue(NetworkState.error(t.message ?: "unknown err"))
//                }
//
//                override fun onResponse(
//                    call: Call<BaseResult<BaseListResult<Article>>>,
//                    response: Response<BaseResult<BaseListResult<Article>>>
//                ) {
//                    if (response.isSuccessful) {
//                        val items = response.body()?.data?.datas ?: emptyList()
//                        // clear retry since last request succeeded
//                        retry = null
//                        callback.onResult(items)
//                        networkState.postValue(NetworkState.LOADED)
//                    } else {
//                        retry = {
//                            loadRange(params, callback)
//                        }
//                        networkState.postValue(
//                            NetworkState.error("error code: ${response.code()}")
//                        )
//                    }
//                }
//            }
//        )
    }
}