package com.example.cityguide.feature.auth.presentation.viewmodel

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cityguide.feature.auth.domain.usecase.ForgotPasswordUseCase
import com.example.cityguide.feature.auth.presentation.contract.ForgotPasswordContract.SideEffect
import com.example.cityguide.feature.auth.presentation.contract.ForgotPasswordContract.UiAction
import com.example.cityguide.feature.auth.presentation.contract.ForgotPasswordContract.UiState
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
class ForgotPasswordViewModel
@Inject constructor(
    @ApplicationContext private val context: Context,
    private val forgotPasswordUseCase: ForgotPasswordUseCase,
    private val appNavigator: AppNavigator
)
    :ViewModel(), MVI<UiState, UiAction, SideEffect> by mvi(initialUiState())
{

    init {
        if (isNetworkAvailable()) updateUiState(UiState.Success("")) else updateUiState(UiState.Error("No internet connection"))
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
            is UiAction.OnResetPasswordCLick -> onResetPasswordClick()
        }
    }

    private fun onResetPasswordClick(){
        val currentState = (uiState.value) as? UiState.Success ?: return
        viewModelScope.launch {
            val result = forgotPasswordUseCase(currentState.email)
            result.onSuccess {
                delay(500)
                onCreateToast("Password reset email sent successfully!")
                tryNavigateTo(Destination.SignIn())
                updateUiState(UiState.Success(""))
            }
            result.onFailure {
                onCreateToast("Something is Wrong")
                updateUiState(UiState.Success(""))
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

}

private fun initialUiState(): UiState = UiState.Loading