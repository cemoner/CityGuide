package com.example.cityguide.feature.home.domain.mapping

import com.example.cityguide.feature.home.data.model.PlaceResult
import com.example.cityguide.feature.home.domain.model.Place

fun PlaceResult.toDomainModel(): Place {
    val photoUrl = photos?.firstOrNull()?.photoReference
    return Place(
        name = name,
        address = formattedAddress,
        rating = rating,
        photoReference = photoUrl
    )
}
