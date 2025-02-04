package com.example.cityguide.feature.home.domain.repository

import com.example.cityguide.feature.home.domain.model.Place


interface PlacesRepository {

    suspend fun getPlacesByCityAndCategory(city: String, category: String, countryName: String? = null): Result<List<Place>>
}