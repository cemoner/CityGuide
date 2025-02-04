package com.example.cityguide.feature.home.util

fun getPhotoUrl(photoReference: String, maxWidth: Int = 400): String {
    return "https://maps.googleapis.com/maps/api/place/photo?maxwidth=$maxWidth&photoreference=$photoReference&key=AIzaSyDVMjIHxNx35GAiKxZgZ1numDIv8UGZXOs"
}