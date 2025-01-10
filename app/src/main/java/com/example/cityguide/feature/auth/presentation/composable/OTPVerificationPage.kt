package com.example.cityguide.feature.auth.presentation.composable

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.cityguide.feature.auth.presentation.contract.OTPVerificationContract.SideEffect
import com.example.cityguide.feature.auth.presentation.contract.OTPVerificationContract.UiAction
import com.example.cityguide.feature.auth.presentation.contract.OTPVerificationContract.UiState
import com.example.cityguide.feature.auth.presentation.viewmodel.OTPVerificationViewModel
import com.example.cityguide.mvi.unpack
import kotlinx.coroutines.flow.Flow

@Composable
fun OTPVerificationScreen() {

    val viewModel: OTPVerificationViewModel = hiltViewModel()
    val (uiState, onAction, sideEffect) = viewModel.unpack()

}


@Composable
fun OTPVerificationContent(
    uiState: UiState,
    onAction: (UiAction) -> Unit,
    sideEffect: Flow<SideEffect>,
) {

    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {

        }
    }
}
