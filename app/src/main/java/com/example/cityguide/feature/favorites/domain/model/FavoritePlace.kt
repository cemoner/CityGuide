package com.example.cityguide.feature.favorites.domain.model

import com.example.cityguide.feature.home.domain.model.Place

data class FavoritePlace(
    val place:Place,
    val cityName:String,
    val userId:String
)
