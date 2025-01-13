package com.example.cityguide.feature.auth.presentation.contract



sealed interface ForgotPasswordContract {
    sealed interface UiState{
        object Loading:UiState
        data class Success(val email: String):UiState
        data class Error(val message:String):UiState
    }
    sealed interface UiAction{
        data class OnEmailChange(val email:String):UiAction
        object OnResetPasswordCLick:UiAction

    }
    sealed interface SideEffect{
        data class ShowToast(
            val message: String,
        ) : SideEffect
    }
}