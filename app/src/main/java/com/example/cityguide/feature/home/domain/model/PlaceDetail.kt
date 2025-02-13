package com.example.cityguide.feature.home.domain.model
data class PlaceDetail(
    val name: String,
    val address: String,
    val rating: Double?,
    val latitude: Double,
    val longitude: Double,
    val openingHours: List<String>?,
    val photoReferences: List<String>,
    val editorialSummary: String?,
    val phoneNumber: String? = null,
    val website: String? = null
)
