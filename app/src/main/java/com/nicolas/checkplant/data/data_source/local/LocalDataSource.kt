package com.nicolas.checkplant.data.data_source.local

import com.nicolas.checkplant.data.model.Plant
import kotlinx.coroutines.flow.Flow

interface LocalDataSource {

    fun getPlants(): Flow<List<Plant>>

    suspend fun getPlantById(id: Int): Plant

    suspend fun insertPlant(plant: Plant)

    suspend fun deletePlant(plant: Plant)

}