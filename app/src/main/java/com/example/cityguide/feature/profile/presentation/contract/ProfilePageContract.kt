package com.example.cityguide.feature.profile.presentation.contract


sealed interface ProfilePageContract {
    data class UiState(val name:String, val email:String, val surname:String, val profileImageUrl:String,val editState:Boolean)

    sealed interface UiAction {
        data class ChangeName(val name:String): UiAction
        data class ChangeSurname(val surname:String): UiAction
        data class ChangeProfileImageUrl(val profileImageUrl:String): UiAction
        data class EditProfileState(val editState:Boolean): UiAction
        object DoneEditClick:UiAction
        object OnFavoritesClick:UiAction
        object OnLogOutClick:UiAction
        object OnResetPasswordClick:UiAction
    }

    sealed interface SideEffect {
        data class ShowToast(
            val message: String,
        ) : SideEffect
    }
}