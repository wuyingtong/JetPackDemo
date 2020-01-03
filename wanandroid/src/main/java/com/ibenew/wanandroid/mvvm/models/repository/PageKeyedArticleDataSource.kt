package com.ibenew.wanandroid.mvvm.models.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.blankj.utilcode.util.LogUtils
import com.ibenew.wanandroid.http.*
import com.ibenew.wanandroid.mvvm.models.data.Article
import java.util.concurrent.Executors

/**
 * Create by wuyt on 2019/12/30 15:22
 * {@link }
 * 如果后端API返回数据是分页之后的，可以使用它；
 * 例如：官方Demo中GitHub API中的SearchRespositories就可以返回分页数据，
 * 我们在GitHub API的请求中制定查询关键字和想要的哪一页，同时也可以指明每个页面的项数。
 */
class PageKeyedArticleDataSource(private val api: WanApi) : PageKeyedDataSource<Int, Article>() {

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

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Article>) {
        // ignored, since we only ever append to our initial load
    }

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, Article>
    ) {
        // update network states.
        // we also provide an initial load state to the listeners so that the UI can know when the
        // very first list is loaded.
        networkState.postValue(NetworkState.LOADING)
        initialLoad.postValue(NetworkState.LOADING)

        val response = object : NetworkBoundResource<BaseListResponse<Article>>() {
            override fun createCall(): LiveData<BaseResponse<BaseListResponse<Article>>> {
                return api.getArticles(0)
            }
        }.asLiveData()
        // triggered by a refresh, we better execute sync
        LogUtils.d("Paging：${response.value?.data?.datas?.size}")
        response.observeForever {
            it.data?.let {result->
                retry = null
                networkState.postValue(NetworkState.LOADED)
                initialLoad.postValue(NetworkState.LOADED)
                // 对应后台返回数据中的上一页，下一页
                callback.onResult(result.datas?: emptyList(),result.curPage,result.curPage)
            }
        }
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Article>) {
        // set network value to loading.
        networkState.postValue(NetworkState.LOADING)
        // even though we are using async retrofit API here, we could also use sync
        // it is just different to show that the callback can be called async.
//        api.getArticles(params.requestedLoadSize).enqueue(
//            object : Callback<BaseResult<BaseListResult<Article>>> {
//                override fun onFailure(call: Call<BaseResult<BaseListResult<Article>>>, t: Throwable) {
//                    // keep a lambda for future retry
//                    retry = {
//                        loadAfter(params, callback)
//                    }
//                    // publish the error
//                    networkState.postValue(NetworkState.error(t.message ?: "unknown err"))
//                }
//
//                override fun onResponse(
//                    call: Call<BaseResult<BaseListResult<Article>>>,
//                    response: Response<BaseResult<BaseListResult<Article>>>) {
//                    if (response.isSuccessful) {
//                        val result = response.body()?.data
//                        val items = result?.datas?: emptyList()
//                        // clear retry since last request succeeded
//                        retry = null
//                        // 对应后台返回数据中的下一页
//                        callback.onResult(items,result?.curPage)
//                        networkState.postValue(NetworkState.LOADED)
//                    } else {
//                        retry = {
//                            loadAfter(params, callback)
//                        }
//                        networkState.postValue(
//                            NetworkState.error("error code: ${response.code()}"))
//                    }
//                }
//            }
//        )
    }
}