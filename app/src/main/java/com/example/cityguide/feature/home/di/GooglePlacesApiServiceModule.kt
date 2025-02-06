package com.example.cityguide.feature.home.di

import com.example.cityguide.feature.home.data.api.GooglePlacesApiService
import com.example.cityguide.retrofit.ApiClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class GooglePlacesApiServiceModule {
    @Provides
    @Singleton
    fun providesGooglePlacesApi(): GooglePlacesApiService = ApiClient.createGoogleMapsApi()
}