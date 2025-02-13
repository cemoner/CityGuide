package com.example.cityguide.feature.home.di

import com.example.cityguide.feature.home.data.datasource.remote.GeoDBDataSource
import com.example.cityguide.feature.home.data.repository.GeoDBRepositoryImpl
import com.example.cityguide.feature.home.domain.repository.GeoDBRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object GeoDBRepositoryModule {

    @Provides
    fun providesGeoDBRepository(geoDBDataSource: GeoDBDataSource): GeoDBRepository =
        GeoDBRepositoryImpl(geoDBDataSource)
}