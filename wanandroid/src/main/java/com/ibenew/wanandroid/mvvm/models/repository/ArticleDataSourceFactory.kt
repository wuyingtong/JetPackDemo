package com.ibenew.wanandroid.mvvm.models.repository

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.ibenew.wanandroid.http.WanApi
import com.ibenew.wanandroid.mvvm.models.data.Article

/**
 * Create by wuyt on 2019/12/30 15:51
 * {@link }
 */
class ArticleDataSourceFactory(private val api: WanApi) : DataSource.Factory<Int, Article>() {
    val sourceLiveData = MutableLiveData<PageKeyedArticleDataSource>()
    override fun create(): DataSource<Int, Article> {
        val source = PageKeyedArticleDataSource(api)
        sourceLiveData.postValue(source)
        return source
    }
}