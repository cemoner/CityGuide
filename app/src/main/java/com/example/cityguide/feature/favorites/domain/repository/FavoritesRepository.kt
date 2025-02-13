package com.example.cityguide.feature.favorites.domain.repository

import com.example.cityguide.feature.favorites.domain.model.FavoritePlace

interface FavoritesRepository {
    suspend fun addToFavorites(
        favoritePlace: FavoritePlace
    ):Result<Unit>
    suspend fun deleteFromFavorites(userId: String, placeId: String):Result<Unit>
    suspend fun getFavorites(userId: String): Result<List<FavoritePlace>>

}