package com.example.cityguide.feature.home.data.model

data class WeatherResponse(
    val lat: Double,
    val lon: Double,
    val timezone: String,
    val timezone_offset: Int,
    val current: CurrentWeather
)

data class CurrentWeather(
    val dt: Long,
    val temp: Double,
    val weather: List<WeatherDetail>
)

data class WeatherDetail(
    val description: String
)
