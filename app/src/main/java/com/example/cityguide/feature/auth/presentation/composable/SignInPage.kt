package com.example.cityguide.feature.auth.presentation.composable

import android.app.Activity
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
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
import com.example.cityguide.feature.auth.presentation.contract.SignInContract.SideEffect
import com.example.cityguide.feature.auth.presentation.contract.SignInContract.UiAction
import com.example.cityguide.feature.auth.presentation.contract.SignInContract.UiState
import com.example.cityguide.feature.auth.presentation.viewmodel.SignInViewModel
import com.example.cityguide.mvi.CollectSideEffect
import com.example.cityguide.mvi.unpack
import kotlinx.coroutines.flow.Flow

@Composable
fun SignInScreen(){
    val viewModel: SignInViewModel = hiltViewModel()
    val (uiState,uiAction,sideEffect) = viewModel.unpack()
    SignInContent(uiState,uiAction,sideEffect,viewModel)
}

@Composable
fun SignInContent(uiState: UiState,
                  onAction: (UiAction) -> Unit,
                  sideEffect: Flow<SideEffect>,
                  viewModel: SignInViewModel
){

    val context = LocalContext.current
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp
    val padding = (screenWidth * 0.04f)

    val googleSignInLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartIntentSenderForResult(),
        onResult =  { result ->
        if (result.resultCode == RESULT_OK) {
            result.data?.let { intent ->
                viewModel.handleSignInResult(intent)
            }
        } else {
            viewModel.onCreateToast("Google Sign-In canceled")
        }
    })

    CollectSideEffect(sideEffect) {
        when (it) {
            is SideEffect.ShowToast -> {
                Toast.makeText(context, it.message, Toast.LENGTH_LONG).show()
            }
            is SideEffect.LaunchGoogleSignIn -> {
                val intentSenderRequest = IntentSenderRequest.Builder(it.intentSender).build()
                googleSignInLauncher.launch(intentSenderRequest)
            }
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        when(uiState){
            is UiState.Error -> {
                Column(modifier = Modifier.padding(innerPadding).fillMaxSize(),horizontalAlignment = Alignment.CenterHorizontally,verticalArrangement = Arrangement.Center) {
                    Text(text = "Error: ${uiState.message}")
                }
            }
            is UiState.Loading -> {
                Column(modifier = Modifier.padding(innerPadding).fillMaxSize(),horizontalAlignment = Alignment.CenterHorizontally,verticalArrangement = Arrangement.Center) {
                    CircularProgressIndicator()
                }
            }
            is UiState.Success -> {
                Column(modifier = Modifier.padding(innerPadding).fillMaxSize()) {
                    LazyColumn(modifier = Modifier.padding(horizontal = padding, vertical = padding).fillMaxSize(),horizontalAlignment = Alignment.CenterHorizontally,verticalArrangement = Arrangement.Center) {
                        item{AuthHeader(
                            stringResource(id = R.string.sign_in_header),
                            stringResource(id = R.string.sign_in_sub_header)
                        )}
                        item{Spacer(modifier = Modifier.height(24.dp))}
                        item{AuthInputField(
                            stringResource(id = R.string.enter_email),
                            uiState.email,
                            {onAction(UiAction.OnEmailChange(it))},
                            false
                        )}
                        item{Spacer(modifier = Modifier.height(12.dp))}
                        item{AuthInputField(
                            stringResource(id = R.string.enter_password),
                            uiState.password,
                            {onAction(UiAction.OnPasswordChange(it))},
                            true
                        )}
                        item{Spacer(modifier = Modifier.height(12.dp))}
                        item{Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.End,modifier = Modifier.fillMaxWidth().padding(4.dp)){
                            AuthTextLink(null, stringResource(id = R.string.forgot_password),{onAction(UiAction.OnForgotPasswordClick)})
                        }}
                        item{Spacer(modifier = Modifier.height(24.dp))}
                        item{AuthButton(stringResource(id = R.string.sign_in)) { onAction(UiAction.OnSignInClick) }}
                        item{Spacer(modifier = Modifier.height(24.dp))}
                        item{Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center,modifier = Modifier.fillMaxWidth().padding(4.dp)){
                            AuthTextLink(
                                stringResource(id = R.string.dont_have_account),
                                stringResource(id = R.string.sign_up),
                                {onAction(UiAction.OnSignUpClick)}
                            )
                        }}
                        item{Spacer(modifier = Modifier.height(18.dp))}
                        item{Subtext(stringResource(id = R.string.connect_with))}
                        item{Spacer(modifier = Modifier.height(12.dp))}
                        item{Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center,modifier = Modifier.fillMaxWidth().padding(4.dp)){
                            SocialMediaButton("google", {onAction(UiAction.OnSocialMediaSignInClick("google"))}, R.drawable.google_icon_logo)
                        }}
                    }
                }
            }
        }
    }
}
