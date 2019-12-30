package com.ibenew.wanandroid.mvvm.models.data

import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

/**
 * Create by wuyt on 2019/12/30 13:49
 * {@link }
 */
@Dao
interface ArticleDao {
    /*
     * 查询所有文章
     */
    @Query("SELECT * FROM t_article")
    fun getAllArticles(): DataSource.Factory<Int,Article>

    /*
     * 插入文章列表
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(articles: List<Article>)

}