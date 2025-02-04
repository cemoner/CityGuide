package com.example.cityguide.feature.home.presentation.contract

interface HomePageContract {
    data class UiState(val categories: List<String>)

    sealed interface UiAction {
        data class OnCategoryClick(val category: String) : UiAction

    }
    sealed interface SideEffect {

    }
}