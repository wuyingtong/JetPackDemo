package com.ibenew.sunflower.data

import androidx.room.Embedded
import androidx.room.Relation

/**
 * Create by wuyt on 2019/12/17 17:05
 * {@link }
 */
data class PlantAndGardenPlantings (
    @Embedded
    val plant: Plant,

    @Relation(parentColumn = "id", entityColumn = "plant_id")
    val gardenPlantings: List<GardenPlanting> = emptyList()
)