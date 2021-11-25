package com.nicolas.checkplant.data.data_source.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.nicolas.checkplant.domain.model.Plant

@Database(
    entities = [Plant::class],
    version = 1
)
abstract class PlantDatabase : RoomDatabase() {

    abstract val checkPlantDao: CheckPlantDao

    companion object {
        const val DATABASE_NAME = "plant_db"
    }

}