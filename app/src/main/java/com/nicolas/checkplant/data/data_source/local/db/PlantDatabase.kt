package com.nicolas.checkplant.data.data_source.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.nicolas.checkplant.data.model.ImagePlant
import com.nicolas.checkplant.data.model.Plant
import com.nicolas.checkplant.data.model.PlantName

@Database(
    entities = [
        Plant::class,
        ImagePlant::class,
        PlantName::class
    ],
    version = 2,
    exportSchema = true
)
abstract class PlantDatabase : RoomDatabase() {

    abstract val checkPlantDao: CheckPlantDao

    companion object {
        const val DATABASE_NAME = "plant_db"
    }

}