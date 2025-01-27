package com.example.cityguide.feature.auth.presentation.contract

import android.content.IntentSender

sealed interface SignInContract {
    sealed interface UiState{
        object Loading:UiState
        data class Success(
            val email: String,
            val password: String,
        ):UiState
        data class Error(val message:String):UiState
    }
    sealed interface UiAction{
        data class OnEmailChange(val email:String):UiAction
        data class OnPasswordChange(val password:String):UiAction
        object OnForgotPasswordClick:UiAction
        object OnSignInClick:UiAction
        object OnSignUpClick:UiAction
        data class OnSocialMediaSignInClick(val platform:String):UiAction

    }
    sealed interface SideEffect{
        data class ShowToast(
            val message: String,
        ) : SideEffect
        data class LaunchGoogleSignIn(val intentSender: IntentSender) : SideEffect
    }
}