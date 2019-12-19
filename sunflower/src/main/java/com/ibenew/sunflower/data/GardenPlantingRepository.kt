package com.ibenew.sunflower.data

import android.os.Looper
import android.util.Log
import androidx.lifecycle.LiveData

/**
 * Create by wuyt on 2019/12/17 17:32
 * {@link }
 */
class GardenPlantingRepository private constructor(private val gardenPlantingDao: GardenPlantingDao) {
    suspend fun createGardenPlanting(plantId: String) {
        val gardenPlanting = GardenPlanting(plantId)
        gardenPlantingDao.insertGardenPlanting(gardenPlanting)
    }

    suspend fun removeGardenPlanting(gardenPlanting: GardenPlanting) {
        gardenPlantingDao.deleteGardenPlanting(gardenPlanting)
    }

    fun isPlanted(plantId: String) =
        gardenPlantingDao.isPlanted(plantId)

    fun getPlantedGardens(): LiveData<List<PlantAndGardenPlantings>> {
        Log.d("getPlantedGardens", "${Looper.myLooper() == Looper.getMainLooper()}")
        return gardenPlantingDao.getPlantedGardens()
    }

    companion object {

        // For Singleton instantiation
        @Volatile
        private var instance: GardenPlantingRepository? = null

        fun getInstance(gardenPlantingDao: GardenPlantingDao) =
            instance ?: synchronized(this) {
                instance ?: GardenPlantingRepository(gardenPlantingDao).also { instance = it }
            }
    }
}