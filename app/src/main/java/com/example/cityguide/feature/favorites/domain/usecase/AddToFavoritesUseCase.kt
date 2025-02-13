package com.example.cityguide.feature.favorites.domain.usecase

import com.example.cityguide.feature.favorites.domain.model.FavoritePlace
import com.example.cityguide.feature.favorites.domain.repository.FavoritesRepository
import javax.inject.Inject

class AddToFavoritesUseCase @Inject constructor(private val favoritesRepository: FavoritesRepository)  {
     suspend operator fun invoke(
          favoritePlace: FavoritePlace
     ) = favoritesRepository.addToFavorites(favoritePlace)
}