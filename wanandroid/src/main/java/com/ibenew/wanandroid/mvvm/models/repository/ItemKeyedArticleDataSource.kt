package com.ibenew.wanandroid.mvvm.models.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import androidx.paging.ItemKeyedDataSource
import com.blankj.utilcode.util.LogUtils
import com.ibenew.wanandroid.http.*
import com.ibenew.wanandroid.mvvm.models.data.Article
import java.util.concurrent.Executors

/**
 * Create by wuyt on 2019/12/30 15:22
 * {@link }
 * 如果通过键值请求后端数据；
 * 例如我们需要获取在某个特定日期起Github的前100项代码提交记录，
 * 该日期将成为DataSource的键,ItemKeyedDataSource允许自定义如何加载初始页；
 * 该场景多用于评论信息等类似请求
 */
class ItemKeyedArticleDataSource(private val api: WanApi) : ItemKeyedDataSource<Int, Article>() {
    private var pageNum = 0

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


    override fun getKey(item: Article): Int = item.id

    // 加载上一页数据
    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Article>) {
        // ignored, since we only ever append to our initial load
    }

    // 初始化第一页数据
    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Article>
    ) {
        LogUtils.d("paging loadInitial")
        // update network states.
        // we also provide an initial load state to the listeners so that the UI can know when the
        // very first list is loaded.
        networkState.postValue(NetworkState.LOADING)
        initialLoad.postValue(NetworkState.LOADING)
        api.getArticles(0).also {
            LogUtils.d("paging ${it.value}")
        }
        val response = object : NetworkBoundResource<BaseListResponse<Article>>() {
            override fun createCall(): LiveData<BaseResponse<BaseListResponse<Article>>> {
                return api.getArticles(0)
            }
        }.asLiveData()

        // triggered by a refresh, we better execute sync
        response.map {
            it.data?.let { result ->
                retry = null
                networkState.postValue(NetworkState.LOADED)
                initialLoad.postValue(NetworkState.LOADED)
                // 对应后台返回数据中的上一页，下一页
                callback.onResult(result.datas ?: emptyList(), result.curPage, result.curPage)
            }
        }
    }

    // 加载下一页数据
    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Article>) {
        // set network value to loading.
        networkState.postValue(NetworkState.LOADING)
        // even though we are using async retrofit API here, we could also use sync
        // it is just different to show that the callback can be called async.
//        api.getArticles(++pageNum).enqueue(
//            object : Callback<BaseResult<BaseListResult<Article>>> {
//                override fun onFailure(
//                    call: Call<BaseResult<BaseListResult<Article>>>,
//                    t: Throwable
//                ) {
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
//                            loadAfter(params, callback)
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