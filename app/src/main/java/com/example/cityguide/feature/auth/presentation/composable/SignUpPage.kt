package com.example.cityguide.feature.auth.presentation.composable

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.cityguide.R
import com.example.cityguide.feature.auth.presentation.component.AuthButton
import com.example.cityguide.feature.auth.presentation.component.AuthHeader
import com.example.cityguide.feature.auth.presentation.component.AuthInputField
import com.example.cityguide.feature.auth.presentation.component.AuthTextLink
import com.example.cityguide.feature.auth.presentation.component.SocialMediaButton
import com.example.cityguide.feature.auth.presentation.component.Subtext
import com.example.cityguide.feature.auth.presentation.component.TopBar
import com.example.cityguide.feature.auth.presentation.contract.SignInContract
import com.example.cityguide.feature.auth.presentation.contract.SignUpContract.SideEffect
import com.example.cityguide.feature.auth.presentation.contract.SignUpContract.UiAction
import com.example.cityguide.feature.auth.presentation.contract.SignUpContract.UiState
import com.example.cityguide.feature.auth.presentation.viewmodel.SignUpViewModel
import com.example.cityguide.mvi.CollectSideEffect
import com.example.cityguide.mvi.unpack
import kotlinx.coroutines.flow.Flow

@Composable
fun SignUpScreen(){
    val viewModel: SignUpViewModel = hiltViewModel()
    val (uiState, uiAction, sideEffect) = viewModel.unpack()
    SignUpContent(uiState, uiAction, sideEffect)
}

@Composable
fun SignUpContent(uiState: UiState,
                  onAction: (UiAction) -> Unit,
                  sideEffect: Flow<SideEffect>,){


    val context = LocalContext.current

    CollectSideEffect(sideEffect) {
        when (it) {
            is SideEffect.ShowToast -> {
                Toast.makeText(context, it.message, Toast.LENGTH_LONG).show()
            }
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        when(uiState){
            is UiState.Error -> TODO()
            UiState.Loading -> TODO()
            is UiState.Success -> {
                Column(modifier = Modifier.padding(innerPadding).fillMaxSize()) {
                    Column(modifier = Modifier.padding(horizontal = 24.dp, vertical = 48.dp).fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Top) {
                        AuthHeader(
                            stringResource(id = R.string.sign_up_header),
                            stringResource(id = R.string.sign_up_sub_header)
                        )
                        Spacer(modifier = Modifier.height(24.dp))
                        AuthInputField(
                            stringResource(id = R.string.enter_name),
                            uiState.name, { onAction(UiAction.OnNameChange(it)) }, false
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        AuthInputField(
                            stringResource(id = R.string.enter_email),
                            uiState.email, { onAction(UiAction.OnEmailChange(it)) }, false
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        AuthInputField(
                            stringResource(id = R.string.enter_password),
                            uiState.password, { onAction(UiAction.OnPasswordChange(it)) }, true
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Start,
                            modifier = Modifier.fillMaxWidth().padding(4.dp)
                        ) {
                            AuthTextLink(
                                stringResource(id = R.string.password_requirement), null
                            ) { }
                        }
                        Spacer(modifier = Modifier.height(24.dp))
                        AuthButton(stringResource(id = R.string.sign_up)) {onAction(UiAction.OnSignUpClick)}
                        Spacer(modifier = Modifier.height(24.dp))
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center,
                            modifier = Modifier.fillMaxWidth().padding(4.dp)
                        ) {
                            AuthTextLink(
                                stringResource(id = R.string.already_have_account),
                                stringResource(id = R.string.sign_up),
                                { onAction(UiAction.OnSignInClick) }
                            )
                        }
                        Spacer(modifier = Modifier.height(18.dp))
                        Subtext(stringResource(id = R.string.connect_with))
                        Spacer(modifier = Modifier.height(12.dp))
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center,
                            modifier = Modifier.fillMaxWidth().padding(4.dp)
                        ) {
                            SocialMediaButton("google", {}, R.drawable.google_icon_logo)
                            Spacer(modifier = Modifier.height(12.dp))
                            SocialMediaButton("facebook", {}, R.drawable.facebook_icon_logo)
                            Spacer(modifier = Modifier.height(12.dp))
                            SocialMediaButton("twitter", {}, R.drawable.twitter_icon_logo)
                        }
                    }
                }
            }
        }
    }
}
