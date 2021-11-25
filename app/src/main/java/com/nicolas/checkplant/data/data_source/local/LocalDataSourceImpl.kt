package com.nicolas.checkplant.data.data_source.local

import com.nicolas.checkplant.data.data_source.local.db.CheckPlantDao
import com.nicolas.checkplant.domain.model.Plant
import javax.inject.Inject

class LocalDataSourceImpl @Inject constructor(
    private val checkPlantDao: CheckPlantDao
) : LocalDataSource {

    override fun getPlants() = checkPlantDao.getPlants()

    override suspend fun getPlantById(id: Int) = checkPlantDao.getPlantById(id)

    override suspend fun insertPlant(plant: Plant) {
        checkPlantDao.insertPlant(plant)
    }

    override suspend fun deletePlant(plant: Plant) {
        checkPlantDao.deletePlant(plant)
    }
}