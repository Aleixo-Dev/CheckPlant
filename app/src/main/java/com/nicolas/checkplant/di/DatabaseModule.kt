package com.nicolas.checkplant.di

import android.app.Application
import androidx.room.Room
import com.nicolas.checkplant.data.data_source.local.LocalDataSource
import com.nicolas.checkplant.data.data_source.local.LocalDataSourceImpl
import com.nicolas.checkplant.data.data_source.local.db.CheckPlantDao
import com.nicolas.checkplant.data.data_source.local.db.PlantDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun providePlantDatabase(app: Application): PlantDatabase {
        return Room.databaseBuilder(
            app,
            PlantDatabase::class.java,
            PlantDatabase.DATABASE_NAME
        ).allowMainThreadQueries().build()
    }

    @Provides
    @Singleton
    fun provideRepository(database: PlantDatabase): LocalDataSource {
        return LocalDataSourceImpl(database.checkPlantDao)
    }

    @Provides
    @Singleton
    fun provideLocalDataSource(plantDatabase: PlantDatabase): CheckPlantDao {
        return plantDatabase.checkPlantDao
    }
}