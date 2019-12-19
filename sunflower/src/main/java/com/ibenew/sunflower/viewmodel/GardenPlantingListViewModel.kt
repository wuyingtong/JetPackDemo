package com.ibenew.sunflower.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.ibenew.sunflower.data.GardenPlantingRepository
import com.ibenew.sunflower.data.PlantAndGardenPlantings

/**
 * Create by wuyt on 2019/12/17 17:45
 * {@link }
 */
class GardenPlantingListViewModel internal constructor(
    gardenPlantingRepository: GardenPlantingRepository
) : ViewModel() {

    val plantAndGardenPlantings: LiveData<List<PlantAndGardenPlantings>> =
        gardenPlantingRepository.getPlantedGardens()

}