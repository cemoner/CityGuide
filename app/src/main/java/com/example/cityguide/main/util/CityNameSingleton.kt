package com.example.cityguide.main.util

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

object CityNameSingleton {
    private val _cityName = MutableStateFlow("")

    val cityName: StateFlow<String> get() = _cityName

    fun setName(name: String) {
        _cityName.value = name
    }
}

object CountryNameSingleton {
    private val _countryName = MutableStateFlow("")

    val countryName: StateFlow<String> get() = _countryName

    fun setName(name: String) {
        _countryName.value = name
    }
}

object Coordinates {
    var longitude: Double = 0.0
    var latitude: Double = 0.0

    fun setCoordinates(long: Double, lat: Double) {
        longitude = long
        latitude = lat
    }

    fun getCoordinates(): Pair<Double, Double> {
        return Pair(longitude, latitude)
    }
}