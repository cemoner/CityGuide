package com.example.cityguide.feature.profile.presentation.composable

import android.widget.Toast
import androidx.activity.result.IntentSenderRequest
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Key
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.example.cityguide.R
import com.example.cityguide.feature.auth.presentation.contract.SignInContract
import com.example.cityguide.feature.profile.presentation.component.ProfileButton
import com.example.cityguide.feature.profile.presentation.component.ProfileInputField
import com.example.cityguide.feature.profile.presentation.contract.ProfilePageContract.SideEffect
import com.example.cityguide.feature.profile.presentation.contract.ProfilePageContract.UiAction
import com.example.cityguide.feature.profile.presentation.contract.ProfilePageContract.UiState
import com.example.cityguide.feature.profile.presentation.viewmodel.ProfilePageViewModel
import com.example.cityguide.mvi.CollectSideEffect
import com.example.cityguide.ui.theme.LightSubTextColor
import kotlinx.coroutines.flow.Flow

@Composable
fun ProfileScreen( uiState: UiState,
                   onAction: (UiAction) -> Unit,
                   sideEffect: Flow<SideEffect>,
                   viewModel:ProfilePageViewModel
){

    ProfileContent(uiState,onAction,sideEffect,viewModel)
}

@Composable
fun ProfileContent(
    uiState: UiState,
    onAction: (UiAction) -> Unit,
    sideEffect: Flow<SideEffect>,
    viewModel: ProfilePageViewModel
){

    val context = LocalContext.current
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp
    val padding = (screenWidth * 0.025f)
    val profileImageUrl = ProfileImageUrlSingleton.profileImageUrl.collectAsState()


    CollectSideEffect(sideEffect) {
        when (it) {
            is SideEffect.ShowToast -> {
                Toast.makeText(context, it.message, Toast.LENGTH_LONG).show()
            }
        }
    }

    LazyColumn(modifier = Modifier.padding(horizontal = padding, vertical = 0.dp).fillMaxSize(),horizontalAlignment = Alignment.CenterHorizontally,verticalArrangement = Arrangement.Center) {
        item{Box(modifier = Modifier.background(MaterialTheme.colorScheme.background, shape = CircleShape).padding(4.dp)){
            if(uiState.profileImageUrl != ""){
                AsyncImage(
                    model = uiState.profileImageUrl,
                    contentDescription = "Profile Image",
                    modifier = Modifier
                        .size(128.dp)
                        .clip(CircleShape)
                        .background(color = MaterialTheme.colorScheme.surface)
                )
            }
            else {
                Icon(
                    imageVector = Icons.Filled.Person,
                    contentDescription = "Default Profile Icon",
                    tint = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier.size(112.dp)
                )
            }
        }}
        item{Spacer(modifier = Modifier.height(16.dp))}
        item{Text(text = uiState.name, style = MaterialTheme.typography.titleMedium,color = MaterialTheme.colorScheme.onBackground)}
        item{Spacer(modifier = Modifier.height(8.dp))}
        item{Text(text = uiState.email, style = MaterialTheme.typography.bodyMedium,color = LightSubTextColor)}
        item{Spacer(modifier = Modifier.height(16.dp))}
        item {
            Column(modifier = Modifier.fillMaxSize().padding(padding)) {
                if (!uiState.editState) {
                    ProfileButton(
                        stringResource(R.string.favorites),
                        { onAction(UiAction.OnFavoritesClick) },
                        Icons.Default.Favorite
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    ProfileButton(
                        stringResource(R.string.reset_password),
                        { onAction(UiAction.OnResetPasswordClick) },
                        Icons.Default.Key
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    ProfileButton(
                        stringResource(R.string.logout),
                        { onAction(UiAction.OnLogOutClick) },
                        Icons.AutoMirrored.Filled.Logout
                    )
                } else {
                    Text(
                        text = stringResource(R.string.first_name),
                        style = MaterialTheme.typography.titleSmall
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    ProfileInputField(
                        stringResource(R.string.first_name),
                        uiState.name,
                        { onAction(UiAction.ChangeName(it)) },
                        { viewModel.isInputValidName(it) })
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = stringResource(R.string.last_name),
                        style = MaterialTheme.typography.titleSmall
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    ProfileInputField(
                        stringResource(R.string.last_name),
                        uiState.surname,
                        { onAction(UiAction.ChangeSurname(it)) },
                        { viewModel.isInputValidName(it) })
                    Spacer(modifier = Modifier.height(12.dp))
                }
            }
        }
    }
}