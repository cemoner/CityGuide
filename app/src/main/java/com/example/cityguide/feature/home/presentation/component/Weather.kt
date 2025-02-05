package com.example.cityguide.feature.home.presentation.component

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import com.example.cityguide.main.util.CityNameSingleton
import com.example.cityguide.main.util.Coordinates
import com.example.cityguide.main.util.CountryNameSingleton


@Composable
fun Weather(){
    val city = CityNameSingleton.cityName.collectAsState()
    val country = CountryNameSingleton.countryName.collectAsState()
    val coordinates = Coordinates.getCoordinates()
}