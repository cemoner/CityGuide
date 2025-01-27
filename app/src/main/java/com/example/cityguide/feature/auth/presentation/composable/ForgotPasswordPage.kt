package com.example.cityguide.feature.auth.presentation.composable

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.cityguide.R
import com.example.cityguide.feature.auth.presentation.component.AuthButton
import com.example.cityguide.feature.auth.presentation.component.AuthHeader
import com.example.cityguide.feature.auth.presentation.component.AuthInputField
import com.example.cityguide.feature.auth.presentation.contract.ForgotPasswordContract.SideEffect
import com.example.cityguide.feature.auth.presentation.contract.ForgotPasswordContract.UiAction
import com.example.cityguide.feature.auth.presentation.contract.ForgotPasswordContract.UiState
import com.example.cityguide.feature.auth.presentation.viewmodel.ForgotPasswordViewModel
import com.example.cityguide.mvi.unpack
import kotlinx.coroutines.flow.Flow

@Composable
fun ForgotPasswordScreen(){
    val viewModel: ForgotPasswordViewModel = hiltViewModel()
    val (uiState,uiAction,sideEffect) = viewModel.unpack()
    ForgotPasswordContent(uiState,uiAction,sideEffect)
}

@Composable
fun ForgotPasswordContent(uiState: UiState,
                           onAction: (UiAction) -> Unit,
                           sideEffect: Flow<SideEffect>){

    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp
    val padding = (screenWidth * 0.04f)

    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        when(uiState){
            is UiState.Error -> TODO()
            UiState.Loading -> TODO()
            is UiState.Success -> {
                Column(modifier = Modifier.padding(innerPadding).fillMaxSize()) {
                    LazyColumn(modifier = Modifier.padding(padding, padding).fillMaxSize(),horizontalAlignment = Alignment.CenterHorizontally,verticalArrangement = Arrangement.Center) {
                        item{AuthHeader(
                            stringResource(id = R.string.forgot_password_header),
                            stringResource(id = R.string.forgot_password_sub_header)
                        )}
                        item{Spacer(modifier = Modifier.height(24.dp))}
                        item{AuthInputField(
                            stringResource(id = R.string.enter_email),
                            uiState.email,
                            {onAction(UiAction.OnEmailChange(it))},
                            false
                        )}
                        item{Spacer(modifier = Modifier.height(24.dp))}
                        item{AuthButton(stringResource(id = R.string.reset_password)) { onAction(UiAction.OnResetPasswordCLick) }}
                    }
                }
            }
        }
    }
}