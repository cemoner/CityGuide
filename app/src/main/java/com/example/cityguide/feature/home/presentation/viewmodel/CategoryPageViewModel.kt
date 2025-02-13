package com.example.cityguide.feature.home.presentation.viewmodel

import android.location.Location
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.example.cityguide.common.CommonViewModel
import com.example.cityguide.feature.auth.domain.usecase.GetUserIdUseCase
import com.example.cityguide.feature.favorites.domain.usecase.AddToFavoritesUseCase
import com.example.cityguide.feature.favorites.domain.usecase.DeleteFromFavoritesUseCase
import com.example.cityguide.feature.favorites.domain.usecase.GetFavoritesUseCase
import com.example.cityguide.feature.home.domain.usecase.GetPlacesByCityUseCase
import com.example.cityguide.feature.home.presentation.contract.CategoryPageContract
import com.example.cityguide.feature.home.presentation.contract.CategoryPageContract.SideEffect
import com.example.cityguide.feature.home.presentation.contract.CategoryPageContract.UiAction
import com.example.cityguide.feature.home.presentation.contract.CategoryPageContract.UiState
import com.example.cityguide.main.util.CityNameSingleton
import com.example.cityguide.main.util.Coordinates
import com.example.cityguide.main.util.CountryNameSingleton
import com.example.cityguide.mvi.MVI
import com.example.cityguide.mvi.mvi
import com.example.cityguide.navigation.model.Destination
import com.example.cityguide.navigation.navigator.AppNavigator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoryPageViewModel
    @Inject constructor(
        savedStateHandle: SavedStateHandle,
        val getPlacesByCityUseCase: GetPlacesByCityUseCase,
        appNavigator: AppNavigator,
        addToFavorites: AddToFavoritesUseCase,
        deleteFromFavorites: DeleteFromFavoritesUseCase,
        getFavoritesUseCase: GetFavoritesUseCase,
        getUserIdUseCase: GetUserIdUseCase
        ):
    CommonViewModel(
        appNavigator,
        addToFavorites,
        deleteFromFavorites,
        getFavoritesUseCase,
        getUserIdUseCase
    ),
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
        when (action) {
            is UiAction.OnSortClick -> {
                val uiState = uiState.value as UiState.Success
                when(action.sort){
                    is CategoryPageContract.SortType.Distance -> {updateUiState(
                        UiState.Success(
                            places = uiState.places,
                            categoryName = uiState.categoryName,
                            searchText = uiState.searchText,
                            searchPlaces =  uiState.places.sortedBy {calculateDistance(it.longitude,it.latitude)}
                        ))}
                    is CategoryPageContract.SortType.Name -> {updateUiState(
                        UiState.Success(
                            places = uiState.places,
                            categoryName = uiState.categoryName,
                            searchText = uiState.searchText,
                            searchPlaces =  uiState.places.sortedBy {it.name}
                        ))}
                    is CategoryPageContract.SortType.Rating -> updateUiState(
                        UiState.Success(
                            places = uiState.places,
                            categoryName = uiState.categoryName,
                            searchText = uiState.searchText,
                            searchPlaces =  uiState.places.sortedByDescending {it.rating}
                        ))
                }
            }
            is UiAction.OnSearchTextChange -> {
                onSearchTextChange(action.text)
            }

            is UiAction.OnPlaceClick -> {
                navigateWithArgs(Destination.PlaceDetail(action.placeId))
            }

            is UiAction.onFavoriteClick ->{
                viewModelScope.launch { onFavoriteClick(action.place)
                }
            }
        }
    }

    fun onCreateToast(message: String) {
        viewModelScope.launch {
            emitSideEffect(SideEffect.ShowToast(message))
        }
    }

    private fun onSearchTextChange(text: String) {
        val uiState = uiState.value as UiState.Success
        updateUiState(
            UiState.Success(
                places = uiState.places,
                categoryName = uiState.categoryName,
                searchText = text,
                searchPlaces = uiState.places.filter { it.name.contains(text, ignoreCase = true) }
            ))
    }

    private fun calculateDistance(longitude: Double, latitude: Double): Int {
        val userLocation = Coordinates.coordinates.value

        val startPoint = Location("userLocation").apply {
            this.latitude = userLocation.first
            this.longitude = userLocation.second
        }

        val endPoint = Location("placeLocation").apply {
            this.latitude = latitude
            this.longitude = longitude
        }
        val distanceInMeters = startPoint.distanceTo(endPoint)

        return (distanceInMeters / 1000).toInt()
    }

    private fun getCities(category:String){
        viewModelScope.launch {
            val cities = getPlacesByCityUseCase(CityNameSingleton.cityName.value,category,CountryNameSingleton.countryName.value)

            cities.onSuccess {
                updateUiState(
                    UiState.Success(
                        places = it.sortedBy {it.name},
                        categoryName = category,
                        searchText = "",
                        searchPlaces = it.sortedBy {it.name}
                    ))
            }
            cities.onFailure {
                updateUiState(UiState.Error(it.message.toString()))
            }
        }
    }
}
private fun initialUiState():UiState = UiState.Loading