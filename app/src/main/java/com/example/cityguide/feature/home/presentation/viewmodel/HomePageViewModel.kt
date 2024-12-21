package com.example.cityguide.feature.home.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.example.cityguide.main.contract.MainContract.UiState
import com.example.cityguide.main.contract.MainContract.UiAction
import com.example.cityguide.main.contract.MainContract.SideEffect
import com.example.cityguide.mvi.MVI
import com.example.cityguide.mvi.mvi
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomePageViewModel
    @Inject constructor(

    ): ViewModel(),
    MVI<UiState, UiAction, SideEffect> by mvi(
        initialUiState()
    ){
        override fun onAction(action: UiAction) {

    }
}
private fun initialUiState():UiState = UiState()