package com.example.cityguide.feature.home.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.example.cityguide.feature.home.presentation.contract.PlaceDetailContract.SideEffect
import com.example.cityguide.feature.home.presentation.contract.PlaceDetailContract.UiAction
import com.example.cityguide.feature.home.presentation.contract.PlaceDetailContract.UiState
import com.example.cityguide.mvi.MVI
import com.example.cityguide.mvi.mvi
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PlaceDetailViewModel
    @Inject constructor(): ViewModel(), MVI<UiState, UiAction, SideEffect> by mvi(
    initialUiState()
) {

}

private fun initialUiState():UiState = UiState(null)