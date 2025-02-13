package com.example.cityguide.feature.home.domain.usecase

import com.example.cityguide.feature.home.domain.repository.GeoDBRepository
import javax.inject.Inject

class GetCitySuggestionsUseCase @Inject constructor(private val geoDBRepository: GeoDBRepository) {
    suspend operator fun invoke(namePrefix: String) = geoDBRepository.getCitySuggestions(namePrefix)
}