package com.example.cityguide.feature.home.presentation.contract

import com.example.cityguide.feature.home.domain.model.Place

interface CategoryPageContract {
    sealed interface UiState {
        data class Success(val places: List<Place>):UiState
        object Loading : UiState
        data class Error(val message: String):UiState
    }

    sealed interface UiAction {

    }
    sealed interface SideEffect {

    }
}