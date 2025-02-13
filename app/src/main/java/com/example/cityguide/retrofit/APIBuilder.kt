package com.example.cityguide.retrofit

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private const val GOOGLE_MAPS_BASE_URL = "https://maps.googleapis.com/maps/api/"
    private const val WEATHER_BASE_URL = "https://api.openweathermap.org/data/3.0/"
    private const val GEODB_BASE_URL = "https://wft-geo-db.p.rapidapi.com/"

    private fun createRetrofitInstance(baseUrl: String): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private fun createRetrofitInstance(baseUrl: String, client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val googleMapsRetrofit: Retrofit by lazy {
        createRetrofitInstance(GOOGLE_MAPS_BASE_URL)
    }

    val weatherRetrofit: Retrofit by lazy {
        createRetrofitInstance(WEATHER_BASE_URL)
    }

    private val geoDbOkHttpClient: OkHttpClient by lazy {
        OkHttpClient.Builder()
            .addInterceptor { chain ->
                val newRequest = chain.request().newBuilder()
                    .addHeader("X-RapidAPI-Key", "cafc7213f9mshd51ac63003487f8p1bc1eejsn9829c2e43c28")
                    .addHeader("X-RapidAPI-Host", "wft-geo-db.p.rapidapi.com")
                    .build()
                chain.proceed(newRequest)
            }
            .build()
    }

    val geoDbRetrofit: Retrofit by lazy {
        createRetrofitInstance(GEODB_BASE_URL, geoDbOkHttpClient)
    }
}

object ApiClient {
    inline fun <reified T> createGoogleMapsApi(): T =
        RetrofitClient.googleMapsRetrofit.create(T::class.java)

    inline fun <reified T> createWeatherApi(): T =
        RetrofitClient.weatherRetrofit.create(T::class.java)

    inline fun <reified T> createGeoDbApi(): T =
        RetrofitClient.geoDbRetrofit.create(T::class.java)
}
