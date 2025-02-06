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
    private val _coordinates = MutableStateFlow(Pair(0.0, 0.0))
    val coordinates: StateFlow<Pair<Double, Double>> get() = _coordinates

    fun setCoordinates(long: Double, lat: Double) {
        _coordinates.value = Pair(long, lat)
    }
}