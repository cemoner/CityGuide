package com.example.cityguide.feature.auth.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.example.cityguide.feature.auth.presentation.contract.SignUpContract.UiState
import com.example.cityguide.feature.auth.presentation.contract.SignUpContract.UiAction
import com.example.cityguide.feature.auth.presentation.contract.SignUpContract.SideEffect
import com.example.cityguide.mvi.MVI
import com.example.cityguide.mvi.mvi
import dagger.hilt.android.lifecycle.HiltViewModel
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.lifecycle.viewModelScope
import com.example.cityguide.feature.auth.domain.usecase.SignUpUseCase
import com.example.cityguide.navigation.model.Destination
import com.example.cityguide.navigation.navigator.AppNavigator
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel
@Inject constructor(
    @ApplicationContext private val context: Context,
    private val appNavigator: AppNavigator,
    private val signUpUseCase: SignUpUseCase
)
    :ViewModel(), MVI<UiState, UiAction, SideEffect> by mvi(initialUiState())
{

    init {
        if (isNetworkAvailable()) updateUiState(UiState.Success("","","")) else updateUiState(UiState.Error("No internet connection"))
    }

    fun isNetworkAvailable(): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = connectivityManager.activeNetwork
        val networkCapabilities = connectivityManager.getNetworkCapabilities(activeNetwork)
        return networkCapabilities != null && networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
    }

    override fun onAction(action: UiAction) {
        when(action){
            is UiAction.OnNameChange -> onNameChange(action.name)
            is UiAction.OnEmailChange -> onEmailChange(action.email)
            is UiAction.OnPasswordChange -> onPasswordChange(action.password)
            is UiAction.OnSignUpClick -> onSignUpClick()
            is UiAction.OnSignInClick -> tryNavigateTo(Destination.SignIn())
            is UiAction.OnSocialMediaSignInClick -> onSocialMediaSignInClick()
        }
    }


    private fun tryNavigateTo(destination:String){
        viewModelScope.launch {
            appNavigator.tryNavigateTo(destination)
        }
    }

    private fun onSocialMediaSignInClick() {

    }

    private fun onSignUpClick() {
        val currentState = (uiState.value) as? UiState.Success ?: return
        viewModelScope.launch {
            val result = signUpUseCase(currentState.name,currentState.email,currentState.password)
            result.onSuccess {
                onCreateToast("Account created successfully! Taking you to the login page...")
                delay(500)
                tryNavigateTo(Destination.SignIn())
            }
            result.onFailure {
                onCreateToast(it.message.toString())
            }
        }
    }

    private fun onCreateToast(message: String) {
        viewModelScope.launch {
            emitSideEffect(SideEffect.ShowToast(message))
        }
    }

    private fun onNameChange(text: String) {
        val currentState = (uiState.value) as? UiState.Success ?: return
        updateUiState(newUiState = currentState.copy(name = text))
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