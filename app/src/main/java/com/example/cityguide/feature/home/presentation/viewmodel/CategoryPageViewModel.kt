package com.example.cityguide.feature.home.presentation.viewmodel

import android.location.Location
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
import com.example.cityguide.navigation.navigator.AppNavigator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
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

    private fun navigate(destination:String){
        viewModelScope.launch {
            appNavigator.tryNavigateTo(destination)
        }
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

    private fun navigateWithArgs(destination:String){
        viewModelScope.launch {
            appNavigator.tryNavigateTo(destination)
        }
    }

}
private fun initialUiState():UiState = UiState.Loading