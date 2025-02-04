package com.example.cityguide.feature.home.data.api

import com.example.cityguide.feature.home.data.model.PlacesResponse
import com.example.cityguide.retrofit.API
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface GooglePlacesApiService: API {
    @GET("place/textsearch/json")
    suspend fun getPlaces(
        @Query("query") query: String,
        @Query("key") apiKey: String,
        @Query("pagetoken") pageToken: String? = null
    ): Response<PlacesResponse>
}
