package com.example.cityguide.feature.auth.presentation.viewmodel

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cityguide.feature.auth.domain.model.UserData
import com.example.cityguide.feature.auth.domain.usecase.*
import com.example.cityguide.feature.auth.presentation.contract.SignInContract.SideEffect
import com.example.cityguide.feature.auth.presentation.contract.SignInContract.UiAction
import com.example.cityguide.feature.auth.presentation.contract.SignInContract.UiState
import com.example.cityguide.main.util.ProfileEmailSingleton
import com.example.cityguide.mvi.MVI
import com.example.cityguide.mvi.mvi
import com.example.cityguide.navigation.model.Destination
import com.example.cityguide.navigation.navigator.AppNavigator
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val appNavigator: AppNavigator,
    private val googleSignInUseCase: GoogleSignInUseCase,
    private val signInUseCase: SignInUseCase,
    private val handleSignInWithIntentUseCase: GoogleSignInWithIntentUseCase,
    private val getProfileUrlUseCase: GetProfileUrlUseCase,
    private val getProfileNameUseCase: GetProfileNameUseCase,
    private val getEmailUseCase: GetEmailUseCase
) : ViewModel(), MVI<UiState, UiAction, SideEffect> by mvi(initialUiState()) {

    init {
        if (isNetworkAvailable()) {
            updateUiState(UiState.Success(email = "", password = ""))
        } else {
            updateUiState(UiState.Error("No internet connection"))
        }
    }

    override fun onAction(action: UiAction) {
        when (action) {
            is UiAction.OnEmailChange -> onEmailChange(action.email)
            is UiAction.OnPasswordChange -> onPasswordChange(action.password)
            is UiAction.OnSignInClick -> onSignInClick()
            is UiAction.OnSocialMediaSignInClick -> startGoogleSignIn()
            is UiAction.OnForgotPasswordClick -> tryNavigateTo(Destination.ForgotPassword())
            is UiAction.OnSignUpClick -> tryNavigateTo(Destination.SignUp())
        }
    }

    private fun onSignInClick() {
        val currentState = (uiState.value) as? UiState.Success ?: return
        viewModelScope.launch {
            updateUiState(UiState.Loading)
            val result = signInUseCase(currentState.email, currentState.password)
            result.onSuccess {
                handleLoggedInUser()
                onCreateToast("Login successful! Taking you to your home page...")
                tryNavigateTo(Destination.Home())
                updateUiState(UiState.Success(email = "", password = ""))
            }
            result.onFailure {
                updateUiState(UiState.Success("", ""))
                onCreateToast(it.message ?: "Login failed")
            }
        }
    }


    private fun startGoogleSignIn() {
        viewModelScope.launch {
            val intentSender = googleSignInUseCase()
            if (intentSender != null) {
                emitSideEffect(SideEffect.LaunchGoogleSignIn(intentSender))
            } else {
                emitSideEffect(SideEffect.ShowToast("Failed to initiate Google Sign-In"))
            }
        }
    }

    private suspend fun handleLoggedInUser() {
        val profileUrl = getProfileUrlUseCase()
        ProfileImageUrlSingleton.setProfileImageUrl(profileUrl)

        val profileName = getProfileNameUseCase()
        ProfileNameSingleton.setName(profileName)

        val email = getEmailUseCase()
        ProfileEmailSingleton.setEmail(email)
    }




    fun handleSignInResult(intent: Intent) {
        viewModelScope.launch {
            updateUiState(UiState.Loading)
            val result = handleSignInWithIntentUseCase(intent)
            result.data?.let {

                handleLoggedInUser()
                onCreateToast("Google Sign-In successful!")
                tryNavigateTo(Destination.Home())
            } ?: run {
                updateUiState(UiState.Success("",""))
                onCreateToast(result.error ?: "Sign-in failed")
            }
        }
    }

    private fun onEmailChange(email: String) {
        val currentState = (uiState.value) as? UiState.Success ?: return
        updateUiState(currentState.copy(email = email))
    }

    private fun onPasswordChange(password: String) {
        val currentState = (uiState.value) as? UiState.Success ?: return
        updateUiState(currentState.copy(password = password))
    }

    private fun isNetworkAvailable(): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = connectivityManager.activeNetwork
        val networkCapabilities = connectivityManager.getNetworkCapabilities(activeNetwork)
        return networkCapabilities != null && networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
    }

    fun onCreateToast(message: String) {
        viewModelScope.launch {
            emitSideEffect(SideEffect.ShowToast(message))
        }
    }

    private fun tryNavigateTo(destination: String) {
        viewModelScope.launch {
            appNavigator.tryNavigateTo(destination)
            delay(500)
            updateUiState(UiState.Success("",""))
        }
    }
}

private fun initialUiState(): UiState = UiState.Loading
