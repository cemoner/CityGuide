package com.example.cityguide.feature.home.data.datasource.remote

import com.example.cityguide.feature.home.data.api.WeatherApi
import com.example.cityguide.feature.home.data.model.WeatherResponse
import com.example.cityguide.retrofit.ApiHandler
import com.example.cityguide.retrofit.NetworkResult
import javax.inject.Inject
import javax.inject.Named

class WeatherDataSource
@Inject constructor(
    private val weatherApi: WeatherApi,
    @Named("weatherApiKey") private val apiKey: String
) : ApiHandler {
    suspend fun getWeather(lat: Double, lon: Double): Result<WeatherResponse> {
        return try {
            val result = handleApi {  weatherApi.getWeather(lat,lon,apiKey) }
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
