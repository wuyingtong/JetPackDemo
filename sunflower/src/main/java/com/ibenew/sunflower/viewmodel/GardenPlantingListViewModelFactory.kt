package com.ibenew.sunflower.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ibenew.sunflower.data.GardenPlantingRepository

/**
 * Create by wuyt on 2019/12/17 17:34
 * {@link }
 */
class GardenPlantingListViewModelFactory(private val repository: GardenPlantingRepository) :
    ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return GardenPlantingListViewModel(repository) as T
    }
}