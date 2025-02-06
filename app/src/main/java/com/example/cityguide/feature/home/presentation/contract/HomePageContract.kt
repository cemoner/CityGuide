package com.example.cityguide.feature.home.presentation.contract

import com.example.cityguide.feature.home.domain.model.Weather

interface HomePageContract {
    data class UiState(val categories: List<String>,
                       val weatherUiState: WeatherUiState
    )

    sealed interface UiAction {
        data class OnCategoryClick(val category: String) : UiAction

    }
    sealed interface SideEffect {

    }
    sealed class WeatherUiState {
        object Loading : WeatherUiState()
        data class Success(val weather: Weather) : WeatherUiState()
        data class Error(val message: String) : WeatherUiState()
    }
}