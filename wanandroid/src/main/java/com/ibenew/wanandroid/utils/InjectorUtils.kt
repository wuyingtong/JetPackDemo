package com.ibenew.wanandroid.utils

import android.content.Context
import com.ibenew.wanandroid.mvvm.models.AppDatabase
import com.ibenew.wanandroid.mvvm.models.repository.HomeRepository
import com.ibenew.wanandroid.mvvm.viewmodel.HomeViewModelFactory

/**
 * Create by wuyt on 2019/12/30 11:42
 * {@link }
 */
object InjectorUtils {

    private fun getHomeRepository(context: Context):HomeRepository{
        return HomeRepository.getInstance(AppDatabase.getInstance(context.applicationContext).articleDao())
    }

    fun provideHomeViewModelFactory(context: Context):HomeViewModelFactory{
        return HomeViewModelFactory(getHomeRepository(context))
    }
}