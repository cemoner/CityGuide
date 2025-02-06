package com.example.cityguide.feature.home.data.api

import com.example.cityguide.feature.home.data.model.WeatherResponse
import com.example.cityguide.retrofit.API
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi:API {
    @GET("onecall")
    suspend fun getWeather(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("appid") apiKey: String,
        @Query("units") units: String = "metric",
        @Query("exclude") exclude: String = "minutely"
    ): Response<WeatherResponse>
}
