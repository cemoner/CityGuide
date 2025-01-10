package com.example.cityguide.feature.profile.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.example.cityguide.feature.profile.presentation.contract.ProfilePageContract.SideEffect
import com.example.cityguide.feature.profile.presentation.contract.ProfilePageContract.UiState
import com.example.cityguide.feature.profile.presentation.contract.ProfilePageContract.UiAction
import com.example.cityguide.mvi.MVI
import com.example.cityguide.mvi.mvi
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProfilePageViewModel @Inject constructor(

):ViewModel(), MVI<UiState, UiAction, SideEffect> by mvi(
    initialUiState()
) {
    init {
        updateUiState(newUiState = UiState.Success("","",""))
    }
}

private fun initialUiState():UiState = UiState.Loading