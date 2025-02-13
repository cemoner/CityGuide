package com.example.cityguide.feature.home.data.repository

import com.example.cityguide.feature.home.data.datasource.remote.GeoDBDataSource
import com.example.cityguide.feature.home.domain.mapping.toDomainModel
import com.example.cityguide.feature.home.domain.model.City
import com.example.cityguide.feature.home.domain.repository.GeoDBRepository
import javax.inject.Inject

class GeoDBRepositoryImpl @Inject constructor(val citiesDataSource: GeoDBDataSource): GeoDBRepository  {
    override suspend fun getCitySuggestions(namePrefix: String): Result<List<City>> {
        return try {
            val responseResult = citiesDataSource.getCitySuggestions(namePrefix)
            responseResult.fold(
                onSuccess = { cityResponse ->
                    val cities = cityResponse.data.map { it.toDomainModel() }
                            Result.success(cities)
                            },
                onFailure = { error ->
                    throw error
                }
            )
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}