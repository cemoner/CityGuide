package com.example.cityguide.feature.home.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cityguide.feature.home.domain.usecase.GetWeatherUseCase
import com.example.cityguide.feature.home.presentation.contract.HomePageContract.SideEffect
import com.example.cityguide.feature.home.presentation.contract.HomePageContract.UiAction
import com.example.cityguide.feature.home.presentation.contract.HomePageContract.UiState
import com.example.cityguide.feature.home.presentation.contract.HomePageContract.WeatherUiState
import com.example.cityguide.main.util.Coordinates
import com.example.cityguide.mvi.MVI
import com.example.cityguide.mvi.mvi
import com.example.cityguide.navigation.model.Destination
import com.example.cityguide.navigation.navigator.AppNavigator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
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
        viewModelScope.launch {
            Coordinates.coordinates.collectLatest { (lat, lon) ->
                if (lat != 0.0 && lon != 0.0) {
                    fetchWeather(lat, lon)
                }
            }
        }
    }

    private fun fetchWeather(lat: Double, lon: Double) {
        viewModelScope.launch {
            try {
                val weather = getWeatherUseCase(lat,lon)
                weather.onSuccess {
                    updateUiState(uiState.value.copy(weatherUiState = WeatherUiState.Success(it)))
                }
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
    "Health & Wellness"), WeatherUiState.Loading
)