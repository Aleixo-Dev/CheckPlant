package com.nicolas.checkplant.data.data_source.local.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.nicolas.checkplant.data.model.*
import kotlinx.coroutines.flow.Flow

@Dao
interface CheckPlantDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNamePlant(plantName: PlantName)

    @Query("SELECT * FROM plant_names")
    fun getAllPlantNames(): Flow<List<PlantName>>


    //New*
    @Query("SELECT * FROM plants, images where plantId = imagePlantId AND plantId = :id")
    fun getImagesFromProgress(id: Int): Flow<List<ImagePlant>>

    @Query("SELECT * FROM images")
    fun getAllImages(): Flow<List<ImagePlant>>

    @Query("SELECT * FROM plants")
    fun getPlants(): Flow<List<Plant>>

    @Query("SELECT * FROM plants where plantId = :id")
    suspend fun getPlantById(id: Int): Plant

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertImage(imagePlant: ImagePlant)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPlant(plant: Plant)

    @Delete
    fun deletePlant(plant: Plant)

}