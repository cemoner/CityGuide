package com.example.cityguide.feature.profile.presentation.composable

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.cityguide.feature.profile.presentation.contract.ProfilePageContract.SideEffect
import com.example.cityguide.feature.profile.presentation.contract.ProfilePageContract.UiAction
import com.example.cityguide.feature.profile.presentation.contract.ProfilePageContract.UiState
import com.example.cityguide.feature.profile.presentation.viewmodel.ProfilePageViewModel
import com.example.cityguide.mvi.unpack
import kotlinx.coroutines.flow.Flow


@Composable
fun ProfileScreen(){
    val viewModel: ProfilePageViewModel = hiltViewModel()
    val (uiState,uiAction,sideEffect) = viewModel.unpack()
    ProfileContent(uiState,uiAction,sideEffect)
}


@Composable
fun ProfileContent(
    uiState: UiState,
    onAction: (UiAction) -> Unit,
    sideEffect: Flow<SideEffect>,
){
    when(uiState){
        is UiState.Loading -> {}
        is UiState.Success -> {
            Column {
                Text("Hello")
            }
        }
        is UiState.Error -> {}
    }
}