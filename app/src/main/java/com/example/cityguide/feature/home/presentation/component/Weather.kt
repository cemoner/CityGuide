package com.example.cityguide.feature.home.presentation.component

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import com.example.cityguide.main.util.CityNameSingleton


@Composable
fun Weather(){
    val city = CityNameSingleton.cityName.collectAsState()
}