package com.nicolas.checkplant.data.repository

import com.nicolas.checkplant.data.data_source.local.LocalDataSource
import com.nicolas.checkplant.data.model.Plant
import com.nicolas.checkplant.domain.repository.CheckPlantRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CheckPlantRepositoryImpl @Inject constructor(
    private val localDataSource: LocalDataSource
) : CheckPlantRepository {

    override fun getPlants(): Flow<List<Plant>> {
        return localDataSource.getPlants()
    }

    override suspend fun getPlantById(id: Int): Plant {
        return localDataSource.getPlantById(id)
    }

    override suspend fun insertPlant(plant: Plant) {
        localDataSource.insertPlant(plant)
    }

    override suspend fun deletePlant(plant: Plant) {
        localDataSource.deletePlant(plant)
    }
}