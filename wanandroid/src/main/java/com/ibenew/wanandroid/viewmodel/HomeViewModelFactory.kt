package com.ibenew.wanandroid.viewmodel

import androidx.lifecycle.ViewModelProvider
import com.ibenew.wanandroid.models.repository.HomeRepository

/**
 * Create by wuyt on 2019/12/18 12:14
 * {@link }
 */
class HomeViewModelFactory(private val repository: HomeRepository) :
    ViewModelProvider.NewInstanceFactory() {

//    @Suppress("UNCHECKED_CAST")
//    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
//        return HomeViewModel(repository) as T
//    }
}