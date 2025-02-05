package com.example.cityguide.feature.home.domain.model

data class Weather(
    val city: String,
    val country: String,
    val temperature: Double,
    val description: String
)