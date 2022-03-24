package com.nicolas.checkplant.data.data_source.local

import com.nicolas.checkplant.data.data_source.local.db.CheckPlantDao
import com.nicolas.checkplant.data.model.ImagePlant
import com.nicolas.checkplant.data.model.Plant
import com.nicolas.checkplant.data.model.PlantName
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocalDataSourceImpl @Inject constructor(
    private val checkPlantDao: CheckPlantDao
) : LocalDataSource {

    override suspend fun insertPlantName(plantName: PlantName) {
        checkPlantDao.insertNamePlant(plantName)
    }

    override fun getAllPlantName(): Flow<List<PlantName>> {
        return checkPlantDao.getAllPlantNames()
    }

    override fun getPlants() = checkPlantDao.getPlants()

    override fun getAllImages() = checkPlantDao.getAllImages()

    override fun getImagesFromProgress(id: Int) = checkPlantDao.getImagesFromProgress(id)

    override suspend fun insertImage(imagePlant: ImagePlant) {
        checkPlantDao.insertImage(imagePlant)
    }

    override suspend fun getPlantById(id: Int) = checkPlantDao.getPlantById(id)

    override suspend fun insertPlant(plant: Plant) {
        checkPlantDao.insertPlant(plant)
    }


    override suspend fun deletePlant(plant: Plant) {
        checkPlantDao.deletePlant(plant)
    }

    override suspend fun deleteImagePlant(imagePlant: ImagePlant) {
        checkPlantDao.deleteImagePlant(imagePlant)
    }
}