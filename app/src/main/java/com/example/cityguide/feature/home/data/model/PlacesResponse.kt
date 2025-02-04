package com.example.cityguide.feature.home.data.model

import com.google.gson.annotations.SerializedName

data class PlacesResponse(
    val results: List<PlaceResult>,
    @SerializedName("next_page_token")
    val nextPageToken: String?
)