package com.example.cityguide.feature.home.presentation.contract

interface HomePageContract {
    sealed interface UiState {
        data object Success:UiState
        data object Loading:UiState
        data class Error(val message:String):UiState
    }

    sealed interface UiAction {
        data class OnNameChange(val name:String):UiAction
        data class OnSurNameChange(val surname:String):UiAction
        data class OnPhoneNumberChange(val phoneNumber:String):UiAction
        data class OnPhotoUrlChange(val photoUrl:String):UiAction
        data object OnLogoutClick:UiAction
        data object OnFavoritesClick:UiAction
    }
    sealed interface SideEffect {

    }
}