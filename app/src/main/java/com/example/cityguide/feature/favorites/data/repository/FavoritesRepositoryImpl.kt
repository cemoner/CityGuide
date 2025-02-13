package com.example.cityguide.feature.favorites.data.repository

import com.example.cityguide.feature.favorites.data.datasource.FavoritesDataSource
import com.example.cityguide.feature.favorites.domain.model.FavoritePlace
import com.example.cityguide.feature.favorites.domain.repository.FavoritesRepository
import com.example.cityguide.retrofit.ApiHandler
import javax.inject.Inject

class FavoritesRepositoryImpl @Inject constructor(
    private val favoritesDataSource: FavoritesDataSource
) : FavoritesRepository, ApiHandler {

    override suspend fun addToFavorites(
        favoritePlace: FavoritePlace
    ): Result<Unit> {
        return favoritesDataSource.addToFavorites(favoritePlace)
    }

    override suspend fun deleteFromFavorites(userId: String, placeId: String): Result<Unit> {
        return favoritesDataSource.deleteFromFavorites(userId, placeId)
    }

    override suspend fun getFavorites(userId: String): Result<List<FavoritePlace>> {
        return favoritesDataSource.getFavorites(userId)
    }
}
