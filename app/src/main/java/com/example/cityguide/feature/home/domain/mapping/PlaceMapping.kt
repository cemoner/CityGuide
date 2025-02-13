package com.example.cityguide.feature.home.domain.mapping

import com.example.cityguide.feature.home.data.model.PlaceDetailResult
import com.example.cityguide.feature.home.data.model.PlaceResult
import com.example.cityguide.feature.home.domain.model.Place
import com.example.cityguide.feature.home.domain.model.PlaceDetail

fun PlaceResult.toDomainModel(): Place {
    val photosList = photos?.mapNotNull { it.photoReference } ?: emptyList()
    return Place(
        placeId = placeId,
        name = name,
        address = formattedAddress,
        rating = rating,
        photoReferences = photosList,
        latitude = geometry.location.lat,
        longitude = geometry.location.lng
    )
}

fun PlaceDetailResult.toDomainModel(): PlaceDetail {
    return PlaceDetail(
        name = name,
        address = formattedAddress,
        rating = rating,
        phoneNumber = formattedPhoneNumber,
        website = website,
        openingHours = openingHours?.weekdayText,
        photoReferences = photos?.map { it.photoReference } ?: emptyList(),
        latitude = geometry.location.lat,
        longitude = geometry.location.lng,
        editorialSummary = editorialSummary?.overview
    )
}