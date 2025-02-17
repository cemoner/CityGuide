package com.example.cityguide.feature.home.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cityguide.feature.home.domain.usecase.GetPlaceDetailsUseCase
import com.example.cityguide.feature.home.presentation.contract.PlaceDetailContract.SideEffect
import com.example.cityguide.feature.home.presentation.contract.PlaceDetailContract.UiAction
import com.example.cityguide.feature.home.presentation.contract.PlaceDetailContract.UiState
import com.example.cityguide.mvi.MVI
import com.example.cityguide.mvi.mvi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlaceDetailViewModel
    @Inject constructor(
        savedStateHandle: SavedStateHandle,
        private val getPlaceDetailsUseCase: GetPlaceDetailsUseCase
    ): ViewModel(), MVI<UiState, UiAction, SideEffect> by mvi(
    initialUiState()
) {
    init {
        viewModelScope.launch {
            val placeId =
                savedStateHandle.get<String>("placeId")
                    ?: throw IllegalArgumentException("Invalid or missing placeId")
            getPlaceDetails(placeId)
            Log.d("PlaceDetailViewModel", "PlaceId: $placeId")
        }

    }

    private suspend fun getPlaceDetails(placeId:String){
        val result = getPlaceDetailsUseCase(placeId)
        result.onSuccess {
            updateUiState(newUiState = UiState(it))
        }
        result.onFailure {
            Log.d("PlaceDetailViewModel", "Error: ${it.message}")
        }
    }

}

private fun initialUiState():UiState = UiState(null)