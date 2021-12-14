package com.nicolas.checkplant.data.repository

import com.nicolas.checkplant.data.data_source.local.LocalDataSource
import com.nicolas.checkplant.data.model.ImagePlant
import com.nicolas.checkplant.data.model.Plant
import com.nicolas.checkplant.data.model.PlantName
import com.nicolas.checkplant.domain.repository.CheckPlantRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CheckPlantRepositoryImpl @Inject constructor(
    private val localDataSource: LocalDataSource
) : CheckPlantRepository {

    override suspend fun insertPlantName(plantName: PlantName) {
        localDataSource.insertPlantName(plantName)
    }

    override fun getAllPlantName(): Flow<List<PlantName>> {
        return localDataSource.getAllPlantName()
    }

    override fun getPlants(): Flow<List<Plant>> {
        return localDataSource.getPlants()
    }

    override fun getAllImages(): Flow<List<ImagePlant>> {
        return localDataSource.getAllImages()
    }

    override fun getImagesFromProgress(id : Int) = localDataSource.getImagesFromProgress(id)

    override suspend fun insertImage(imagePlant: ImagePlant) {
        localDataSource.insertImage(imagePlant)
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