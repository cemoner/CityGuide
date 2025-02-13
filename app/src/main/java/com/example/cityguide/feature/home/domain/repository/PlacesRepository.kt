package com.example.cityguide.feature.home.domain.repository

import com.example.cityguide.feature.home.domain.model.Place
import com.example.cityguide.feature.home.domain.model.PlaceDetail


interface PlacesRepository {

    suspend fun getPlacesByCityAndCategory(city: String, category: String, countryName: String? = null): Result<List<Place>>

    suspend fun getPlaceDetails(placeId: String): Result<PlaceDetail>
}