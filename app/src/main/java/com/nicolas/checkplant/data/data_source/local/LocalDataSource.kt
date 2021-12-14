package com.nicolas.checkplant.data.data_source.local

import com.nicolas.checkplant.data.model.ImagePlant
import com.nicolas.checkplant.data.model.Plant
import com.nicolas.checkplant.data.model.PlantName
import kotlinx.coroutines.flow.Flow

interface LocalDataSource {


    suspend fun insertPlantName(plantName: PlantName)

    fun getAllPlantName() : Flow<List<PlantName>>

    fun getPlants(): Flow<List<Plant>>

    fun getAllImages() : Flow<List<ImagePlant>>

    fun getImagesFromProgress(id : Int): Flow<List<ImagePlant>>

    suspend fun insertImage(imagePlant: ImagePlant)

    suspend fun getPlantById(id: Int): Plant

    suspend fun insertPlant(plant: Plant)

    suspend fun deletePlant(plant: Plant)

}