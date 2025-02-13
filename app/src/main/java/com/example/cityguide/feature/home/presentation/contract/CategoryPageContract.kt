package com.example.cityguide.feature.home.presentation.contract

import androidx.annotation.StringRes
import com.example.cityguide.R
import com.example.cityguide.feature.home.domain.model.Place

interface CategoryPageContract {
    sealed interface UiState {
        data class Success(
            val places: List<Place>,
            val searchPlaces: List<Place>,
            val categoryName:String,
            val currentSort: SortType = SortType.Name(),
            val searchText:String):UiState
        object Loading : UiState
        data class Error(val message: String):UiState
    }

    sealed interface UiAction {
        data class OnSortClick(val sort: SortType) : UiAction
        data class OnSearchTextChange(val text: String) : UiAction
        data class OnPlaceClick(val placeId: String) : UiAction
        data class onFavoriteClick(val place: Place) : UiAction

    }
    sealed interface SideEffect {
        data class ShowToast(val message: String) : SideEffect

    }

    sealed interface SortType {
        @get:StringRes
        val nameRes: Int

        data class Name(override val nameRes: Int = R.string.sort_name) : SortType
        data class Rating(override val nameRes: Int = R.string.sort_rating) : SortType
        data class Distance(override val nameRes: Int = R.string.sort_distance) : SortType
    }
}