package com.example.cityguide.feature.home.domain.model

data class Place(
    val name: String,
    val address: String,
    val rating: Double?,
    val photoReference: String?,
    val latitude: Double,
    val longitude: Double
)