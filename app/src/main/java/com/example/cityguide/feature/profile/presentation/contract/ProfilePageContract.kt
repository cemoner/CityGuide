package com.example.cityguide.feature.profile.presentation.contract

sealed interface ProfilePageContract {
    sealed interface UiState {
        data object Loading : UiState
        data class Success(val name:String, val surname:String, val profileImageUrl:String) : UiState
        data class Error(val message: String) : UiState
    }

    sealed interface UiAction {
        data class ChangeName(val name:String): UiAction
        data class ChangeSurname(val surname:String): UiAction
        data class ChangeProfileImageUrl(val profileImageUrl:String): UiAction
    }

    sealed interface SideEffect {

    }
}