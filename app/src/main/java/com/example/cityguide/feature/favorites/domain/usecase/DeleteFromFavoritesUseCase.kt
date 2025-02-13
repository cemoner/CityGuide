package com.example.cityguide.feature.favorites.domain.usecase

import com.example.cityguide.feature.favorites.domain.repository.FavoritesRepository
import javax.inject.Inject

class DeleteFromFavoritesUseCase @Inject constructor(private val favoritesRepository: FavoritesRepository)  {
    suspend operator fun invoke(userId:String,placeId: String) = favoritesRepository.deleteFromFavorites(userId,placeId)
}