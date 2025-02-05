package com.example.cityguide.feature.home.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cityguide.feature.home.domain.usecase.GetWeatherUseCase
import com.example.cityguide.feature.home.presentation.contract.HomePageContract
import com.example.cityguide.feature.home.presentation.contract.HomePageContract.UiState
import com.example.cityguide.feature.home.presentation.contract.HomePageContract.UiAction
import com.example.cityguide.feature.home.presentation.contract.HomePageContract.SideEffect
import com.example.cityguide.feature.home.presentation.contract.HomePageContract.WeatherUiState
import com.example.cityguide.main.util.CityNameSingleton
import com.example.cityguide.main.util.Coordinates
import com.example.cityguide.main.util.CountryNameSingleton
import com.example.cityguide.mvi.MVI
import com.example.cityguide.mvi.mvi
import com.example.cityguide.navigation.model.Destination
import com.example.cityguide.navigation.navigator.AppNavigator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomePageViewModel
    @Inject constructor(
         val appNavigator: AppNavigator,
        val getWeatherUseCase: GetWeatherUseCase
    ): ViewModel(),
    MVI<UiState, UiAction, SideEffect> by mvi(
        initialUiState()
    ) {
    override fun onAction(action: UiAction) {
        when (action) {
            is UiAction.OnCategoryClick -> navigateWithArgs(Destination.CategoryPage(action.category))
        }
    }


    init {
        fetchWeather()
    }

    private fun fetchWeather() {
        viewModelScope.launch {
            try {
                val coordinates = Coordinates.getCoordinates()
                val weather = getWeatherUseCase(coordinates.second, coordinates.first)
                Log.d("WeatherViewModel", "Weather: $weather")
            } catch (e: Exception) {

            }
        }
    }
    private fun navigate(destination:String){
        viewModelScope.launch {
            appNavigator.tryNavigateTo(destination)
        }
    }

    private fun navigateWithArgs(destination:String){
        viewModelScope.launch {
            appNavigator.tryNavigateTo(destination)
        }
    }
}
private fun initialUiState():UiState = UiState(listOf(
    "Gastronomy",
    "Night Life",
    "Cultural & Entertainment",
    "Accommodations",
    "Parks & Nature",
    "Religious Sites",
    "Sports & Recreation",
    "Health & Wellness"), WeatherUiState.Loading)