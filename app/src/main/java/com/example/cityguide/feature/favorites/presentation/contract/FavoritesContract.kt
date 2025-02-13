package com.example.cityguide.feature.favorites.presentation.contract

import com.example.cityguide.feature.favorites.domain.model.FavoritePlace
import com.example.cityguide.feature.home.domain.model.Place

interface FavoritesContract {
    data class UiState(val places: List<FavoritePlace>)

    sealed interface UiAction {
        data class OnPlaceClick(val placeId: String) : UiAction
        data class onFavoriteClick(val place: Place) : UiAction
    }
    sealed interface SideEffect {
        data class ShowToast(val message: String) : SideEffect
    }
}