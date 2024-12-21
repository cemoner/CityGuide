package com.example.cityguide.main.viewmodel

import androidx.lifecycle.ViewModel
import com.example.cityguide.main.contract.MainContract.SideEffect
import com.example.cityguide.main.contract.MainContract.UiAction
import com.example.cityguide.main.contract.MainContract.UiState
import com.example.cityguide.mvi.MVI
import com.example.cityguide.mvi.mvi
import com.example.cityguide.navigation.navigator.AppNavigator
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel
@Inject
constructor(
    val navigator: AppNavigator,
) : ViewModel(),
    MVI<UiState, UiAction, SideEffect> by mvi(initialUiState()) {
    val navigationChannel = navigator.navigationChannel

    override fun onAction(action: UiAction) {
    }
}

private fun initialUiState():UiState = UiState()
