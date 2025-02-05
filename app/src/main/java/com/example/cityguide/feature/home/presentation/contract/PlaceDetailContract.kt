package com.example.cityguide.feature.home.presentation.contract

import com.example.cityguide.feature.home.domain.model.Place

interface PlaceDetailContract {
    data class UiState(val place: Place?)

    sealed interface UiAction {
    }
    sealed interface SideEffect {

    }
}