package com.example.cityguide.di

import com.example.cityguide.feature.home.data.api.GooglePlacesApiService
import com.example.cityguide.feature.home.data.datasource.remote.PlacesDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataSourceModule {

    @Provides
    @Singleton
    fun providePlacesDataSource(
        apiService: GooglePlacesApiService,
        @Named("googleApiKey") apiKey: String
    ): PlacesDataSource = PlacesDataSource(apiService, apiKey)

    @Provides
    @Singleton
    @Named("googleApiKey")
    fun provideGoogleApiKey(): String = "AIzaSyDVMjIHxNx35GAiKxZgZ1numDIv8UGZXOs"
}
