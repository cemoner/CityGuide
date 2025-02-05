package com.example.cityguide.feature.home.domain.repository

import com.example.cityguide.feature.home.domain.model.Weather

interface WeatherRepository {
    suspend fun getWeather(lat: Double, lon: Double): Result<Weather>
}