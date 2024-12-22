package com.example.cityguide.feature.home.presentation.contract

interface HomePageContract {
    sealed interface UiState {
        data class Success(val name:String,val profileImageUrl:String? = null):UiState
        data object Loading:UiState
        data class Error(val message:String):UiState
    }

    sealed interface UiAction {
        data object NavigateToProfile:UiAction
    }
    sealed interface SideEffect {

    }
}