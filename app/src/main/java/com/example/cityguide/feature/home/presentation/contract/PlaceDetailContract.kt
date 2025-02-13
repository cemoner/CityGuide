package com.example.cityguide.feature.home.presentation.contract

import com.example.cityguide.feature.home.domain.model.PlaceDetail


interface PlaceDetailContract {
    data class UiState(val placeDetail: PlaceDetail?)

    sealed interface UiAction {

    }
    sealed interface SideEffect {

    }
}