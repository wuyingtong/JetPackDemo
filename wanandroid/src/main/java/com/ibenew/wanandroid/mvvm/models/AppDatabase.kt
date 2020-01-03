package com.ibenew.wanandroid.mvvm.models

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.ibenew.wanandroid.mvvm.models.data.Article
import com.ibenew.wanandroid.mvvm.models.db.ArticleDao
import com.ibenew.wanandroid.utils.DATABASE_NAME

/**
 * Create by wuyt on 2019/12/30 13:43
 * {@link }
 */
@Database(entities = [Article::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun articleDao(): ArticleDao


    companion object {
        @Volatile
        private var instance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }
        }

        private fun buildDatabase(context: Context): AppDatabase {
            return Room.databaseBuilder(context, AppDatabase::class.java, DATABASE_NAME)
                .addCallback(object : RoomDatabase.Callback() {
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        super.onCreate(db)
                        //val request = OneTimeWorkRequestBuilder<SeedDatabaseWorker>().build()
                        //WorkManager.getInstance(context).enqueue(request)
                    }
                })
                .build()
        }
    }
}