package com.example.cityguide.feature.home.data.model

import com.google.gson.annotations.SerializedName

data class PlaceDetailResult(
    val name: String,
    @SerializedName("formatted_address")
    val formattedAddress: String,
    val geometry: Geometry,
    val rating: Double?,
    @SerializedName("opening_hours")
    val openingHours: OpeningHours,
    val photos: List<Photo>?,
    @SerializedName("editorial_summary")
    val editorialSummary: EditorialSummary?,
    @SerializedName("formatted_phone_number")
    val formattedPhoneNumber: String? = null,
    val website: String? = null

)

data class EditorialSummary(
    val language: String,
    val overview: String
)

data class OpeningHours(
    @SerializedName("weekday_text")
    val weekdayText: List<String>?
)
