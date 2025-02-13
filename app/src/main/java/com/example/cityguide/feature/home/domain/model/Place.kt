package com.example.cityguide.feature.home.domain.model

data class Place(
    val placeId: String,
    val name: String,
    val address: String,
    val rating: Double?,
    val photoReferences: List<String>,
    val latitude: Double,
    val longitude: Double,
)