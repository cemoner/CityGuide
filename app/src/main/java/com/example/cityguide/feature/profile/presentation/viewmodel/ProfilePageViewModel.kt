package com.example.cityguide.feature.profile.presentation.viewmodel

import ProfileImageUrlSingleton
import ProfileNameSingleton
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cityguide.feature.auth.domain.usecase.ForgotPasswordUseCase
import com.example.cityguide.feature.auth.domain.usecase.SignOutUseCase
import com.example.cityguide.feature.profile.domain.usecase.UpdateNameUseCase
import com.example.cityguide.feature.profile.domain.usecase.UpdateProfileImageUrl
import com.example.cityguide.feature.profile.domain.usecase.UpdateSurnameUseCase
import com.example.cityguide.feature.profile.presentation.contract.ProfilePageContract.SideEffect
import com.example.cityguide.feature.profile.presentation.contract.ProfilePageContract.UiAction
import com.example.cityguide.feature.profile.presentation.contract.ProfilePageContract.UiState
import com.example.cityguide.main.util.ProfileEmailSingleton
import com.example.cityguide.mvi.MVI
import com.example.cityguide.mvi.mvi
import com.example.cityguide.navigation.navigator.AppNavigator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlinx.coroutines.flow.combine


@HiltViewModel
class ProfilePageViewModel @Inject constructor(
    private val appNavigator: AppNavigator,
    private val updateNameUseCase: UpdateNameUseCase,
    private val updateSurnameUseCase: UpdateSurnameUseCase,
    private val forgotPasswordUseCase: ForgotPasswordUseCase,
    private val updateProfileImageUrl: UpdateProfileImageUrl,
    private val signOutUseCase: SignOutUseCase
) : ViewModel(), MVI<UiState, UiAction, SideEffect> by mvi(
    initialUiState()
) {

    private val originalValues = mutableMapOf<String, String>()

    init {
        viewModelScope.launch {
            combine(
                ProfileEmailSingleton.profileEmail,
                ProfileNameSingleton.profileName,
                ProfileImageUrlSingleton.profileImageUrl
            ) { email, name, profileImageUrl ->
                val firstName = name.substringBefore(" ")
                val lastName = name.substringAfter(" ")

                UiState(
                    name = firstName,
                    surname = lastName,
                    profileImageUrl = profileImageUrl,
                    email = email,
                    editState = false
                )
            }.collect { newState ->
                updateUiState(newState)
                originalValues["name"] = newState.name
                originalValues["surname"] = newState.surname
                originalValues["profileImageUrl"] = newState.profileImageUrl
            }
        }
    }


    override fun onAction(action: UiAction) {
        when (action) {
            is UiAction.ChangeName -> onNameChange(action.name)
            is UiAction.ChangeSurname -> onSurnameChange(action.surname)
            is UiAction.EditProfileState -> onEditStateChange(action.editState)
            is UiAction.ChangeProfileImageUrl -> onProfileImageUrlChange(action.profileImageUrl)
            is UiAction.OnFavoritesClick -> onFavoritesClick()
            is UiAction.OnLogOutClick -> onLogOutClick()
            is UiAction.OnResetPasswordClick -> onResetPasswordClick()
            is UiAction.DoneEditClick -> doneEditClick()
        }
    }

    private fun doneEditClick() = viewModelScope.launch {
        val currentState = uiState.value

        if (isInputValidName(currentState.name) && isInputValidName(currentState.surname)) {

            if (currentState.name != originalValues["name"]) {
                updateNameUseCase(currentState.name)
                originalValues["name"] = currentState.name
            }
            if (currentState.surname != originalValues["surname"]) {
                updateSurnameUseCase(currentState.surname)
                originalValues["surname"] = currentState.surname
            }
            if (currentState.profileImageUrl != originalValues["profileImageUrl"]) {
                updateProfileImageUrl(currentState.profileImageUrl)
                originalValues["profileImageUrl"] = currentState.profileImageUrl
            }

            onEditStateChange(false)
        } else {
            onCreateToast("Invalid input")
        }
    }

    private fun onFavoritesClick() {}

    private fun onLogOutClick() = viewModelScope.launch {
        signOutUseCase()
        appNavigator.clearBackStack()
    }

    private fun onResetPasswordClick() {
        viewModelScope.launch {
            val result = forgotPasswordUseCase(ProfileEmailSingleton.profileEmail.value)
            result.onSuccess {
                delay(500)
                onCreateToast("Password reset email sent successfully!")
            }
            result.onFailure {
                onCreateToast("Something is Wrong")
            }
        }
    }

    private fun onNameChange(name: String) {
        updateUiState(uiState.value.copy(name = name))
    }

    private fun onSurnameChange(surname: String) {
        updateUiState(uiState.value.copy(surname = surname))
    }

    private fun onEditStateChange(editState: Boolean) {
        updateUiState(uiState.value.copy(editState = editState))
    }

    private fun onProfileImageUrlChange(profileImageUrl: String) {
        updateUiState(uiState.value.copy(profileImageUrl = profileImageUrl))
    }

    fun onCreateToast(message: String) {
        viewModelScope.launch {
            emitSideEffect(SideEffect.ShowToast(message))
        }
    }

    private fun tryNavigateTo(destination: String, launchSingleTop: Boolean) {
        viewModelScope.launch {
            appNavigator.tryNavigateTo(destination, isSingleTop = launchSingleTop)
        }
    }

    fun isInputValidName(string: String): Boolean {
        val nameRegex = "^\\p{L}+$".toRegex()
        return string.isNotBlank() && nameRegex.matches(string)
    }
}

private fun initialUiState(): UiState = UiState(
    name = "",
    email = "",
    surname = "",
    profileImageUrl = "",
    editState = false
)


