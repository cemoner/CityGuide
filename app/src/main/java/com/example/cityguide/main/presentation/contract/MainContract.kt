package com.example.cityguide.main.presentation.contract

sealed interface MainContract {
    sealed interface UiState{
        object Loading:UiState
        data class Success(val isUserLoggedIn:Boolean):UiState
        data class Error(val message:String):UiState
    }

    sealed interface UiAction {
        object OnBackButtonClick: UiAction
    }

    sealed interface SideEffect {

    }
}
