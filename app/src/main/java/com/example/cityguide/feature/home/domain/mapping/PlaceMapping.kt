package com.example.cityguide.feature.home.domain.mapping

import com.example.cityguide.feature.home.data.model.PlaceResult
import com.example.cityguide.feature.home.domain.model.Place

fun PlaceResult.toDomainModel(): Place {
    val photoUrl = photos?.firstOrNull()?.photoReference
    val lat = geometry.location.lat
    val lng = geometry.location.lng

    return Place(
        name = name,
        address = formattedAddress,
        rating = rating,
        photoReference = photoUrl,
        latitude = lat,
        longitude = lng
    )
}
