package com.example.cityguide.feature.home.domain.usecase

import com.example.cityguide.feature.home.domain.model.PlaceDetail
import com.example.cityguide.feature.home.domain.repository.PlacesRepository
import javax.inject.Inject

class GetPlaceDetailsUseCase @Inject constructor(
    private val placesRepository: PlacesRepository
) {
    suspend operator fun invoke(
        placeId:String
    ): Result<PlaceDetail> {
        return placesRepository.getPlaceDetails(placeId)
    }
}