package com.example.cityguide.feature.home.data.repository

import android.util.Log
import com.example.cityguide.feature.home.data.datasource.remote.PlacesDataSource
import com.example.cityguide.feature.home.domain.mapping.toDomainModel
import com.example.cityguide.feature.home.domain.model.Place
import com.example.cityguide.feature.home.domain.model.PlaceDetail
import com.example.cityguide.feature.home.domain.repository.PlacesRepository
import kotlinx.coroutines.delay
import javax.inject.Inject
import com.example.cityguide.retrofit.ApiHandler



class PlacesRepositoryImpl @Inject constructor(
    private val placesDataSource: PlacesDataSource
) : PlacesRepository,ApiHandler {

    override suspend fun getPlacesByCityAndCategory(
        city: String,
        category: String,
        countryName: String?
    ): Result<List<Place>> {
        return try {
            val query = if (countryName != null) {
                "$category in $city, $countryName"
            } else {
                "$category in $city"
            }
            val allPlaces = mutableListOf<Place>()
            var pageToken: String? = null

            do {
                val responseResult = placesDataSource.getPlaces(query, pageToken)

                responseResult.fold(
                    onSuccess = { placesResponse ->
                        val places = placesResponse.results.map {
                            Log.d("PlacesRepositoryImpl", "Place: ${it.photos}")
                            it.toDomainModel() }
                        allPlaces.addAll(places)
                        pageToken = placesResponse.nextPageToken
                        if (pageToken != null) {
                            delay(2000)
                        }
                    },
                    onFailure = { error ->
                        return Result.failure(error)
                    }
                )
            } while (pageToken != null)

            Result.success(allPlaces)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getPlaceDetails(placeId: String): Result<PlaceDetail> {
        return try {
            val responseResult = placesDataSource.getPlaceDetails(placeId)
            responseResult.fold(
                onSuccess = { placeDetailResponse ->
                    val placeDetail = placeDetailResponse.result.toDomainModel()
                    Result.success(placeDetail)
                },
                onFailure = { error ->
                    Result.failure(error)
                }
            )
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
