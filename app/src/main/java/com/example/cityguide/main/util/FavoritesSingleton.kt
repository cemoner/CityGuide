package com.example.cityguide.main.util

import androidx.compose.runtime.mutableStateListOf

object FavoritesSingleton {

    private val _favorites = mutableStateListOf<Int>()

    val favorites: List<Int>
        get() = _favorites

    fun addToFavorites(productId: Int) {
        if (!favorites.contains(productId)) {
            _favorites.add(productId)
        }
    }

    fun deleteFromFavorites(productId: Int) {
        _favorites.remove(productId)
    }

    fun isFavorite(productId: Int): Boolean = favorites.contains(productId)

    fun clearFavorites() {
        _favorites.clear()
    }
}
