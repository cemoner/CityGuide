package com.example.cityguide.feature.home.di

import com.example.cityguide.feature.home.data.datasource.remote.PlacesDataSource
import com.example.cityguide.feature.home.data.repository.PlacesRepositoryImpl
import com.example.cityguide.feature.home.domain.repository.PlacesRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object PlacesRepositoryModule {

    @Provides
    fun providesPlacesRepository(placesDataSource: PlacesDataSource): PlacesRepository =
        PlacesRepositoryImpl(placesDataSource)
}