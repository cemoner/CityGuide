package com.example.cityguide.feature.home.domain.repository

import com.example.cityguide.feature.home.domain.model.City

interface GeoDBRepository {
    suspend fun getCitySuggestions(namePrefix: String): Result<List<City>>

}