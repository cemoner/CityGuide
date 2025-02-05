package com.example.cityguide.feature.home.data.repository

import com.example.cityguide.feature.home.data.datasource.remote.WeatherDataSource
import com.example.cityguide.feature.home.domain.mapping.toDomainModel
import com.example.cityguide.feature.home.domain.model.Weather
import com.example.cityguide.feature.home.domain.repository.WeatherRepository
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val weatherDataSource: WeatherDataSource
) : WeatherRepository {

    override suspend fun getWeather(lat: Double, lon: Double): Result<Weather> {
        return try {
            val responseResult = weatherDataSource.getWeather(lat, lon)
            responseResult.map { weatherResponse ->
                weatherResponse.toDomainModel()
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
