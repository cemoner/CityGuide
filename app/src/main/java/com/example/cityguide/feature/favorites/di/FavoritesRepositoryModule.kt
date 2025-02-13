package com.example.cityguide.feature.favorites.di


import com.example.cityguide.feature.favorites.data.datasource.FavoritesDataSource
import com.example.cityguide.feature.favorites.data.repository.FavoritesRepositoryImpl
import com.example.cityguide.feature.favorites.domain.repository.FavoritesRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class FavoritesRepositoryModule {

    @Provides
    fun providesFavoritesRepository(favoritesDataSource: FavoritesDataSource): FavoritesRepository =
        FavoritesRepositoryImpl(favoritesDataSource)
}