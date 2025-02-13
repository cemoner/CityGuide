package com.example.cityguide.feature.home.data.api

import com.example.cityguide.feature.home.data.model.CityResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface GeoDBApiService {
    @GET("v1/geo/cities")
    suspend fun getCities(
        @Query("namePrefix") namePrefix: String,
        @Query("limit") limit: Int = 10
    ): Response<CityResponse>
}