package com.nicolas.checkplant.domain.repository

import com.nicolas.checkplant.data.model.Plant
import kotlinx.coroutines.flow.Flow

interface CheckPlantRepository {

    fun getPlants(): Flow<List<Plant>>

    suspend fun getPlantById(id: Int): Plant

    suspend fun insertPlant(plant: Plant)

    suspend fun deletePlant(plant: Plant)

}