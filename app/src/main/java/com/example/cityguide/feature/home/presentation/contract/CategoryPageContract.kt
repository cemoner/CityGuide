package com.example.cityguide.feature.home.presentation.contract

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


    }
    sealed interface SideEffect {

    }

    sealed interface SortType{
        val name: String
        data class Name(override val name: String = "Name"): SortType
        data class Rating(override val name: String = "Rating"): SortType
        data class Distance(override val name: String = "Distance"): SortType

    }
}