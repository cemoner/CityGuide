package com.example.cityguide.retrofit

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private const val GOOGLE_MAPS_BASE_URL = "https://maps.googleapis.com/maps/api/"
    private const val WEATHER_BASE_URL = "https://api.openweathermap.org/data/3.0/"

    private fun createRetrofitInstance(baseUrl: String): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val googleMapsRetrofit: Retrofit by lazy {
        createRetrofitInstance(GOOGLE_MAPS_BASE_URL)
    }

    val weatherRetrofit: Retrofit by lazy {
        createRetrofitInstance(WEATHER_BASE_URL)
    }
}

object ApiClient {
    inline fun <reified T> createGoogleMapsApi(): T = RetrofitClient.googleMapsRetrofit.create(T::class.java)
    inline fun <reified T> createWeatherApi(): T = RetrofitClient.weatherRetrofit.create(T::class.java)
}
