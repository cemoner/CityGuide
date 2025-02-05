package com.example.cityguide.feature.home.di

import com.example.cityguide.feature.home.data.datasource.remote.WeatherDataSource
import com.example.cityguide.feature.home.data.repository.WeatherRepositoryImpl
import com.example.cityguide.feature.home.domain.repository.WeatherRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object WeatherRepositoryModule {

    @Provides
    fun providesWeatherRepository(weatherDataSource: WeatherDataSource): WeatherRepository =
        WeatherRepositoryImpl(weatherDataSource)
}