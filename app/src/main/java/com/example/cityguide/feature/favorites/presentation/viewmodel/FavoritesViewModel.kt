package com.example.cityguide.feature.favorites.presentation.viewmodel

import androidx.lifecycle.viewModelScope
import com.example.cityguide.common.CommonViewModel
import com.example.cityguide.feature.auth.domain.usecase.GetUserIdUseCase
import com.example.cityguide.feature.favorites.domain.usecase.AddToFavoritesUseCase
import com.example.cityguide.feature.favorites.domain.usecase.DeleteFromFavoritesUseCase
import com.example.cityguide.feature.favorites.domain.usecase.GetFavoritesUseCase
import com.example.cityguide.feature.favorites.presentation.contract.FavoritesContract.SideEffect
import com.example.cityguide.feature.favorites.presentation.contract.FavoritesContract.UiAction
import com.example.cityguide.feature.favorites.presentation.contract.FavoritesContract.UiState
import com.example.cityguide.main.util.FavoritesSingleton
import com.example.cityguide.mvi.MVI
import com.example.cityguide.mvi.mvi
import com.example.cityguide.navigation.model.Destination
import com.example.cityguide.navigation.navigator.AppNavigator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    appNavigator: AppNavigator,
    addToFavorites: AddToFavoritesUseCase,
    deleteFromFavorites: DeleteFromFavoritesUseCase,
    getFavoritesUseCase: GetFavoritesUseCase,
    getUserIdUseCase: GetUserIdUseCase
) : CommonViewModel(
    appNavigator,
    addToFavorites,
    deleteFromFavorites,
    getFavoritesUseCase,
    getUserIdUseCase
),



    MVI<UiState, UiAction, SideEffect> by mvi(
    initialUiState()
) {

    init {
        viewModelScope.launch {
            val favorites = getFavorites()
            if(favorites.isNotEmpty()){
                updateUiState(UiState(favorites))
                for(favorite in favorites){
                    FavoritesSingleton.addToFavorites(
                        favorite.place
                    )
                }
            }
        }
    }

    override fun onAction(action: UiAction) {
        when(action){
            is UiAction.OnPlaceClick -> onPlaceClick(action.placeId)
            is UiAction.onFavoriteClick ->{
                viewModelScope.launch {
                    val result = onFavoriteClick(action.place)
                    if(result){
                        onCreateToast("Added to favorites")
                        updateUiState(UiState(getFavorites()))
                    }
                    else {
                        onCreateToast("Error adding to favorites")
                        updateUiState(UiState(getFavorites()))
                    }
                }
            }
        }
    }
    private fun onPlaceClick(placeId:String) {
        viewModelScope.launch {
            navigateWithArgs(Destination.PlaceDetail(placeId))
        }
    }

    fun onCreateToast(message: String) {
        viewModelScope.launch {
            emitSideEffect(SideEffect.ShowToast(message))
        }
    }
}



private fun initialUiState():UiState = UiState(emptyList())