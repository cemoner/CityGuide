package com.example.cityguide.feature.home.domain.usecase

import com.example.cityguide.feature.home.domain.model.Place
import com.example.cityguide.feature.home.domain.repository.PlacesRepository
import javax.inject.Inject

class GetPlacesByCityUseCase @Inject constructor(
    private val placesRepository: PlacesRepository
) {
    suspend operator fun invoke(
        city: String,
        category: String,
        countryName: String? = null
    ): Result<List<Place>> {
        return placesRepository.getPlacesByCityAndCategory(city, category, countryName)
    }
}