package com.example.cityguide.feature.home.presentation.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cityguide.feature.home.domain.usecase.GetPlacesByCityUseCase
import com.example.cityguide.feature.home.presentation.contract.CategoryPageContract.SideEffect
import com.example.cityguide.feature.home.presentation.contract.CategoryPageContract.UiAction
import com.example.cityguide.feature.home.presentation.contract.CategoryPageContract.UiState
import com.example.cityguide.main.util.CityNameSingleton
import com.example.cityguide.main.util.CountryNameSingleton
import com.example.cityguide.mvi.MVI
import com.example.cityguide.mvi.mvi
import com.example.cityguide.navigation.navigator.AppNavigator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoryPageViewModel
    @Inject constructor(
        val appNavigator: AppNavigator,
        val savedStateHandle: SavedStateHandle,
        val getPlacesByCityUseCase: GetPlacesByCityUseCase,

        ):
    ViewModel(),
    MVI<UiState, UiAction, SideEffect> by mvi(
    initialUiState()
    )
{
        init {
            val category = savedStateHandle.get<String>("category")
            if (category != null) {
                getCities(category)
            }

        }

    override fun onAction(action: UiAction) {
    }

    private fun navigate(destination:String){
        viewModelScope.launch {
            appNavigator.tryNavigateTo(destination)
        }
    }

    private fun getCities(category:String){
        viewModelScope.launch {
            val cities = getPlacesByCityUseCase(CityNameSingleton.cityName.value,category,CountryNameSingleton.countryName.value)

            cities.onSuccess {
                updateUiState(UiState.Success(places = it))
            }
            cities.onFailure {
                updateUiState(UiState.Error(it.message.toString()))
            }
        }
    }

    private fun navigateWithArgs(destination:String){
        viewModelScope.launch {
            appNavigator.tryNavigateTo(destination)
        }
    }

}
private fun initialUiState():UiState = UiState.Loading