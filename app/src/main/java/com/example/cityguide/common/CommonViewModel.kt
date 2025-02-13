package com.example.cityguide.common

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cityguide.feature.auth.domain.usecase.GetUserIdUseCase
import com.example.cityguide.feature.favorites.domain.model.FavoritePlace
import com.example.cityguide.feature.favorites.domain.usecase.AddToFavoritesUseCase
import com.example.cityguide.feature.favorites.domain.usecase.DeleteFromFavoritesUseCase
import com.example.cityguide.feature.favorites.domain.usecase.GetFavoritesUseCase
import com.example.cityguide.feature.home.domain.model.Place
import com.example.cityguide.main.util.CityNameSingleton
import com.example.cityguide.main.util.FavoritesSingleton
import com.example.cityguide.navigation.navigator.AppNavigator
import kotlinx.coroutines.launch


open class CommonViewModel(
    protected val appNavigator: AppNavigator,
    protected val addToFavorites: AddToFavoritesUseCase,
    protected val deleteFromFavorites: DeleteFromFavoritesUseCase,
    protected val getFavoritesUseCase: GetFavoritesUseCase,
    protected val getUserIdUseCase: GetUserIdUseCase
):ViewModel() {

    lateinit var userId: String

    init {
        viewModelScope.launch {
            userId = getUserIdUseCase().toString()
        }
    }

    fun navigateWithArgs(destination: String) {
        viewModelScope.launch {
            appNavigator.tryNavigateTo(destination)
        }
    }

    suspend fun onFavoriteClick(
        place: Place
    ):Boolean {
        if (FavoritesSingleton.isFavorite(place.placeId)) {
            return deleteFromFavorites(place.placeId)

        } else {
            return addToFavorites(place)
        }
    }

    suspend fun getFavorites(): List<FavoritePlace> {
        val result = getFavoritesUseCase(userId)
        result.onSuccess {
            return it
        }
        result.onFailure {
            return emptyList()
        }
        return emptyList()
    }

    suspend fun addToFavorites(
        place: Place
    ): Boolean {
        val result = addToFavorites(
            FavoritePlace
                (
                place,
                CityNameSingleton.cityName.value,
                userId
                        ))
        result.onSuccess {
            FavoritesSingleton.addToFavorites(place)
            return true
        }
        result.onFailure {
            return false
        }
        return false
    }

    suspend fun deleteFromFavorites(placeId:String):Boolean{
        val result = deleteFromFavorites(userId, placeId)
        result.onSuccess {
            FavoritesSingleton.deleteFromFavorites(FavoritesSingleton.favorites.find { it.placeId == placeId }!!)
            return true
        }
        result.onFailure {
            return false
        }
        return false
    }
}