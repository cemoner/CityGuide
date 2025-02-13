package com.example.cityguide.feature.home.di

import com.example.cityguide.feature.home.data.api.GeoDBApiService
import com.example.cityguide.retrofit.ApiClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class GeoDBApiServiceModule {
    @Provides
    @Singleton
    fun providesGeoDbServiceApi(): GeoDBApiService = ApiClient.createGeoDbApi()
}