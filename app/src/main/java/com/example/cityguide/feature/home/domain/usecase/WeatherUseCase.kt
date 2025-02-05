package com.example.cityguide.feature.home.domain.usecase

import com.example.cityguide.feature.home.domain.model.Weather
import com.example.cityguide.feature.home.domain.repository.WeatherRepository
import javax.inject.Inject

class GetWeatherUseCase @Inject constructor(
    private val repository: WeatherRepository
) {
    suspend operator fun invoke(lat: Double, lon: Double): Result<Weather> {
        return repository.getWeather(lat, lon)
    }
}