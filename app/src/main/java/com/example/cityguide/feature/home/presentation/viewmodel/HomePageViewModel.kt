package com.example.cityguide.feature.home.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cityguide.feature.home.presentation.contract.HomePageContract.UiState
import com.example.cityguide.feature.home.presentation.contract.HomePageContract.UiAction
import com.example.cityguide.feature.home.presentation.contract.HomePageContract.SideEffect
import com.example.cityguide.mvi.MVI
import com.example.cityguide.mvi.mvi
import com.example.cityguide.navigation.model.Destination
import com.example.cityguide.navigation.navigator.AppNavigator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomePageViewModel
    @Inject constructor(
        private val appNavigator: AppNavigator
    ): ViewModel(),
    MVI<UiState, UiAction, SideEffect> by mvi(
        initialUiState()
    ){
        override fun onAction(action: UiAction) {
            when(action){
                UiAction.NavigateToProfile -> {navigate(Destination.Profile())}
            }
    }

    private fun navigate(destination:String){
        viewModelScope.launch {
            appNavigator.tryNavigateTo(destination)
        }
    }

}
private fun initialUiState():UiState = UiState.Loading