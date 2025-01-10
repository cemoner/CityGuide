package com.example.cityguide.feature.auth.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.example.cityguide.feature.auth.presentation.contract.OTPVerificationContract.UiState
import com.example.cityguide.feature.auth.presentation.contract.OTPVerificationContract.UiAction
import com.example.cityguide.feature.auth.presentation.contract.OTPVerificationContract.SideEffect
import com.example.cityguide.mvi.MVI
import com.example.cityguide.mvi.mvi
import dagger.hilt.android.lifecycle.HiltViewModel
import androidx.lifecycle.viewModelScope
import com.example.cityguide.navigation.navigator.AppNavigator
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OTPVerificationViewModel
@Inject constructor(
    private val appNavigator: AppNavigator,
)
    :ViewModel(), MVI<UiState, UiAction, SideEffect> by mvi(initialUiState())
{

    override fun onAction(action: UiAction) {
        when(action){
            is UiAction.OnChangeFieldFocused -> TODO()
            is UiAction.OnEnterNumber -> TODO()
            UiAction.OnKeyboardBackCLick -> TODO()
            UiAction.OnVerificationClick -> TODO()
        }
    }


    private fun onVerificationClick() {

    }

    private fun tryNavigateTo(destination:String){
        viewModelScope.launch {
            appNavigator.tryNavigateTo(destination)
        }
    }


}

private fun initialUiState(): UiState = UiState(emptyList())