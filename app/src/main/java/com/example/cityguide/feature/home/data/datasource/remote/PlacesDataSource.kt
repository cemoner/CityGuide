package com.example.cityguide.feature.home.data.datasource.remote

import com.example.cityguide.feature.home.data.api.GooglePlacesApiService
import com.example.cityguide.feature.home.data.model.PlacesResponse
import com.example.cityguide.retrofit.ApiHandler
import com.example.cityguide.retrofit.NetworkResult
import javax.inject.Inject


class PlacesDataSource
    @Inject constructor(
    private val apiService: GooglePlacesApiService,
    private val apiKey: String
) :ApiHandler {
    suspend fun getPlaces(query: String, pageToken: String? = null): Result<PlacesResponse> {
        return try {
            val result = handleApi {  apiService.getPlaces(query, apiKey, pageToken) }
            when(result){
                is NetworkResult.Success -> {
                    Result.success(result.data)
                }
                is NetworkResult.Error -> Result.failure(Exception(result.errorMsg))
                is NetworkResult.Exception -> Result.failure(result.e)
            }
        } catch (e: Exception) {
            throw e
        }
    }
}
