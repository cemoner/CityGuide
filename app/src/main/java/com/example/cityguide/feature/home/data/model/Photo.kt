package com.example.cityguide.feature.home.data.model

import com.google.gson.annotations.SerializedName

data class Photo(
    @SerializedName("photo_reference")
    val photoReference: String,
    val height: Int,
    val width: Int
)