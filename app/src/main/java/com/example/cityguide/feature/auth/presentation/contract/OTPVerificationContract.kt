package com.example.cityguide.feature.auth.presentation.contract

sealed interface OTPVerificationContract {
    data class  UiState(
            val otpCode:List<Int?>,
            val focusedIndex:Int? = null,
            val isValid:Boolean? = null)
    sealed interface UiAction{
        data class OnEnterNumber(val number:Int? ,val index:Int):UiAction
        data class OnChangeFieldFocused(val index: Int):UiAction
        data object OnKeyboardBackCLick:UiAction
        object OnVerificationClick:UiAction

    }
    sealed interface SideEffect{
        object ReduceTime:SideEffect
    }
}