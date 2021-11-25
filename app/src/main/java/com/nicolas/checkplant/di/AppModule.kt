package com.nicolas.checkplant.di

import com.nicolas.checkplant.data.data_source.local.LocalDataSource
import com.nicolas.checkplant.data.repository.CheckPlantRepositoryImpl
import com.nicolas.checkplant.domain.repository.CheckPlantRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideCheckPlantRepository(localDataSource: LocalDataSource) : CheckPlantRepository{
        return CheckPlantRepositoryImpl(localDataSource)
    }
}