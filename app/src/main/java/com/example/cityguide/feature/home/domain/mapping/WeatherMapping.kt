package com.example.cityguide.feature.home.domain.mapping

import com.example.cityguide.feature.home.data.model.WeatherResponse
import com.example.cityguide.feature.home.domain.model.Weather

fun extractCityAndCountry(timezone: String): Pair<String, String> {
    val parts = timezone.split("/")
    // Use the second part as the city (replace underscores with spaces)
    val city = parts.getOrNull(1)?.replace("_", " ") ?: "Unknown City"
    // Use the first part as the country/region; note that this is not an ISO country code
    val country = parts.getOrNull(0) ?: "Unknown Country"
    return city to country
}

fun WeatherResponse.toDomainModel(): Weather {
    val (city, country) = extractCityAndCountry(timezone)
    return Weather(
        city = city,
        country = country,
        temperature = current.temp,
        description = current.weather.firstOrNull()?.description.orEmpty()
    )
}
