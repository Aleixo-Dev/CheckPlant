package com.nicolas.checkplant.domain.repository

import com.nicolas.checkplant.data.model.*
import kotlinx.coroutines.flow.Flow

interface CheckPlantRepository {


    suspend fun insertPlantName(plantName: PlantName)

    fun getAllPlantName() : Flow<List<PlantName>>

    fun getPlants(): Flow<List<Plant>>

    fun getAllImages() : Flow<List<ImagePlant>>

    fun getImagesFromProgress(id : Int): Flow<List<ImagePlant>>

    suspend fun insertImage(imagePlant: ImagePlant)

    suspend fun getPlantById(id: Int): Plant

    suspend fun insertPlant(plant: Plant)

    suspend fun deletePlant(plant: Plant)

    suspend fun deleteImagePlant(imagePlant: ImagePlant)

}