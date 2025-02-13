package com.example.cityguide.feature.home.data.datasource.remote

import com.example.cityguide.feature.home.data.api.GeoDBApiService
import com.example.cityguide.feature.home.data.model.CityResponse
import com.example.cityguide.retrofit.ApiHandler
import com.example.cityguide.retrofit.NetworkResult
import javax.inject.Inject

class GeoDBDataSource @Inject constructor(private val geoDBService: GeoDBApiService):ApiHandler {
    suspend fun getCitySuggestions(query:String): Result<CityResponse>{
        return try {
            val result = handleApi { geoDBService.getCities(query) }
            when(result){
                is NetworkResult.Success -> {
                    Result.success(result.data)
                }
                is NetworkResult.Error -> Result.failure(Exception(result.errorMsg))
                is NetworkResult.Exception -> Result.failure(result.e)
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}