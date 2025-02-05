package com.example.cityguide.feature.home.presentation.composable

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.cityguide.feature.home.presentation.contract.PlaceDetailContract.SideEffect
import com.example.cityguide.feature.home.presentation.contract.PlaceDetailContract.UiAction
import com.example.cityguide.feature.home.presentation.contract.PlaceDetailContract.UiState
import com.example.cityguide.feature.home.presentation.viewmodel.PlaceDetailViewModel
import com.example.cityguide.mvi.unpack
import kotlinx.coroutines.flow.Flow

@Composable
fun PlaceDetailScreen(){
    val viewModel: PlaceDetailViewModel = hiltViewModel()
    val (uiState,uiAction,sideEffect) = viewModel.unpack()
    PlaceDetailContent(viewModel,uiState,uiAction,sideEffect)
}

@Composable
fun PlaceDetailContent(
    viewModel: PlaceDetailViewModel,
    uiState: UiState,
    onAction: (UiAction) -> Unit,
    sideEffect: Flow<SideEffect>,
) {

}