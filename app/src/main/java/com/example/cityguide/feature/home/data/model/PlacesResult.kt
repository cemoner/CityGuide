package com.example.cityguide.feature.home.data.model

import com.google.gson.annotations.SerializedName

data class PlaceResult(
    val name: String,
    @SerializedName("formatted_address")
    val formattedAddress: String,
    val rating: Double?,
    val photos: List<Photo>?,
    val types: List<String>?
)