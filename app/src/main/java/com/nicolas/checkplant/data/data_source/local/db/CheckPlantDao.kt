package com.nicolas.checkplant.data.data_source.local.db

import androidx.room.*
import com.nicolas.checkplant.data.model.Plant
import kotlinx.coroutines.flow.Flow

@Dao
interface CheckPlantDao {

    @Query("SELECT * FROM plants")
    fun getPlants(): Flow<List<Plant>>

    @Query("SELECT * FROM plants where id = :id")
    suspend fun getPlantById(id: Int): Plant

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPlant(plant: Plant)

    @Delete
    fun deletePlant(plant: Plant)

}