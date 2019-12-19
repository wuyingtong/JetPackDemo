package com.ibenew.sunflower.utils

import android.content.Context
import com.ibenew.sunflower.data.AppDatabase
import com.ibenew.sunflower.data.GardenPlantingRepository
import com.ibenew.sunflower.data.PlantRepository
import com.ibenew.sunflower.viewmodel.GardenPlantingListViewModelFactory

/**
 * Create by wuyt on 2019/12/17 17:20
 * {@link }
 */
object InjectorUtils {
    private fun getPlantRepository(context: Context): PlantRepository {
        return PlantRepository.getInstance(
            AppDatabase.getInstance(context.applicationContext).plantDao())
    }

    private fun getGardenPlantingRepository(context: Context): GardenPlantingRepository {
        return GardenPlantingRepository.getInstance(
            AppDatabase.getInstance(context.applicationContext).gardenPlantingDao())
    }

    fun provideGardenPlantingListViewModelFactory(
        context: Context
    ): GardenPlantingListViewModelFactory {
        val repository = getGardenPlantingRepository(context)
        return GardenPlantingListViewModelFactory(repository)
    }

//    fun providePlantListViewModelFactory(context: Context): PlantListViewModelFactory {
//        val repository = getPlantRepository(context)
//        return PlantListViewModelFactory(repository)
//    }
//
//    fun providePlantDetailViewModelFactory(
//        context: Context,
//        plantId: String
//    ): PlantDetailViewModelFactory {
//        return PlantDetailViewModelFactory(getPlantRepository(context),
//            getGardenPlantingRepository(context), plantId)
//    }
}