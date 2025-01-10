package com.example.cityguide.feature.auth.presentation.viewmodel

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cityguide.feature.auth.domain.usecase.GetProfileUrlUseCase
import com.example.cityguide.feature.auth.domain.usecase.IsUserLoggedInUseCase
import com.example.cityguide.feature.auth.domain.usecase.SignInUseCase
import com.example.cityguide.feature.auth.presentation.contract.SignInContract.SideEffect
import com.example.cityguide.feature.auth.presentation.contract.SignInContract.UiAction
import com.example.cityguide.feature.auth.presentation.contract.SignInContract.UiState
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
class SignInViewModel
    @Inject constructor(
        @ApplicationContext private val context: Context,
        private val appNavigator: AppNavigator,
        private val signInUseCase: SignInUseCase,
    )
    :ViewModel(), MVI<UiState, UiAction, SideEffect> by mvi(initialUiState())
{

    init {
        if (isNetworkAvailable()) updateUiState(UiState.Success("","")) else updateUiState(UiState.Error("No internet connection"))
    }

    fun isNetworkAvailable(): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = connectivityManager.activeNetwork
        val networkCapabilities = connectivityManager.getNetworkCapabilities(activeNetwork)
        return networkCapabilities != null && networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
    }

    override fun onAction(action: UiAction) {
        when(action){
            is UiAction.OnEmailChange -> onEmailChange(action.email)
            is UiAction.OnPasswordChange -> onPasswordChange(action.password)
            is UiAction.OnSocialMediaSignInClick -> {}
            is UiAction.OnForgotPasswordClick -> tryNavigateTo(Destination.ForgotPassword())
            is UiAction.OnSignUpClick -> tryNavigateTo(Destination.SignUp())
            is UiAction.OnSignInClick -> onSignInClick()
        }
    }


    private fun onSignInClick(){
        val currentState = (uiState.value) as? UiState.Success ?: return
        viewModelScope.launch {
            val result = signInUseCase(currentState.email,currentState.password)
            result.onSuccess {
                onCreateToast("Login successful! Taking you to your home page...")
                delay(1000)
                tryNavigateTo(Destination.Home())
                updateUiState(UiState.Success("",""))
            }
            result.onFailure {
                onCreateToast(it.message.toString())
                updateUiState(UiState.Success("",""))
            }
        }
    }


    private fun onCreateToast(message: String) {
        viewModelScope.launch {
            emitSideEffect(SideEffect.ShowToast(message))
        }
    }

    private fun tryNavigateTo(destination:String){
        viewModelScope.launch {
            appNavigator.tryNavigateTo(destination)
        }
    }

    private fun onEmailChange(text: String) {
        val currentState = (uiState.value) as? UiState.Success ?: return
        updateUiState(newUiState = currentState.copy(email = text))
    }

    private fun onPasswordChange(text: String) {
        val currentState = (uiState.value) as? UiState.Success ?: return
        updateUiState(newUiState = currentState.copy(password = text))
    }
}

private fun initialUiState(): UiState = UiState.Loading