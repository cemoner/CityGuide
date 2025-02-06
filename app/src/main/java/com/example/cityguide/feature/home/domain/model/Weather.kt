package com.example.cityguide.feature.home.domain.model

data class Weather(
    val city: String,
    val country: String,
    val temperature: Double,
    val description: String,
    val humidity: Int,
    val pressure: Int,
    val windSpeed: Double,
    val windDirection: Int,
    val cloudiness: Int,
    val visibility: Int,
    val uvi: Double,
    val sunrise: Long,
    val sunset: Long
)
