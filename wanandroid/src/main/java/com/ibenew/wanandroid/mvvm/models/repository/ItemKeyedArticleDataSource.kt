package com.ibenew.wanandroid.mvvm.models.repository

import androidx.lifecycle.MutableLiveData
import androidx.paging.ItemKeyedDataSource
import com.ibenew.wanandroid.http.WanApi
import com.ibenew.wanandroid.mvvm.models.data.Article
import com.ibenew.wanandroid.mvvm.models.data.BaseListResult
import com.ibenew.wanandroid.mvvm.models.data.BaseResult
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException
import java.util.concurrent.Executors

/**
 * Create by wuyt on 2019/12/30 15:22
 * {@link }
 */
class ItemKeyedArticleDataSource(private val api: WanApi) : ItemKeyedDataSource<Int, Article>() {

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
        // update network states.
        // we also provide an initial load state to the listeners so that the UI can know when the
        // very first list is loaded.
        networkState.postValue(NetworkState.LOADING)
        initialLoad.postValue(NetworkState.LOADING)

        val request = api.getArticles(1)
        // triggered by a refresh, we better execute sync

        try {
            request.enqueue(object : Callback<BaseResult<BaseListResult<Article>>> {
                override fun onFailure(
                    call: Call<BaseResult<BaseListResult<Article>>>,
                    t: Throwable
                ) {

                }

                override fun onResponse(
                    call: Call<BaseResult<BaseListResult<Article>>>,
                    response: Response<BaseResult<BaseListResult<Article>>>
                ) {
                    if (response.isSuccessful && !response.body()?.data?.datas.isNullOrEmpty()){
                        retry = null
                        val items = response.body()?.data?.datas
                        networkState.postValue(NetworkState.LOADED)
                        initialLoad.postValue(NetworkState.LOADED)
                        callback.onResult(items!!)
                    }
                }

            })

        } catch (ioException: IOException) {
            retry = {
                loadInitial(params, callback)
            }
            val error = NetworkState.error(ioException.message ?: "unknown error")
            networkState.postValue(error)
            initialLoad.postValue(error)
        }

    }

    // 加载下一页数据
    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Article>) {

    }

}