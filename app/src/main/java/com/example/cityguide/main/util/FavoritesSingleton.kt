package com.example.cityguide.main.util

import androidx.compose.runtime.mutableStateListOf
import com.example.cityguide.feature.home.domain.model.Place

object FavoritesSingleton {

    private val _favorites = mutableStateListOf<Place>()

    val favorites: List<Place>
        get() = _favorites

    fun addToFavorites(place: Place) {
        if (!favorites.contains(place)) {
            _favorites.add(place)
        }
    }

    fun deleteFromFavorites(place: Place) {
        _favorites.remove(place)
    }

    fun isFavorite(placeId: String): Boolean = _favorites.any { it.placeId == placeId }
}
