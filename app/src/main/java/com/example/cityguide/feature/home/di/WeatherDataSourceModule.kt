package com.example.cityguide.di

import com.example.cityguide.feature.home.data.api.WeatherApi
import com.example.cityguide.feature.home.data.datasource.remote.WeatherDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object WeatherDataSourceModule {

    @Provides
    @Singleton
    fun providesWeatherDataSource(
        apiService: WeatherApi,
        @Named("weatherApiKey") apiKey: String
    ): WeatherDataSource = WeatherDataSource(apiService,apiKey)

    @Provides
    @Singleton
    @Named("weatherApiKey")
    fun providesWeatherApiKey(): String = "6b52181963494752ebe255c562131013"
}