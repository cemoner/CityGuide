package com.example.cityguide.feature.home.domain.mapping

import com.example.cityguide.feature.home.data.model.WeatherResponse
import com.example.cityguide.feature.home.domain.model.Weather
import com.example.cityguide.main.util.CityNameSingleton
import com.example.cityguide.main.util.CountryNameSingleton


fun WeatherResponse.toDomainModel(): Weather {
    return Weather(
        city = CityNameSingleton.cityName.value,
        country = CountryNameSingleton.countryName.value,
        temperature = current.temp,
        description = current.weather.firstOrNull()?.description.orEmpty(),
        humidity = current.humidity,
        pressure = current.pressure,
        windSpeed = current.wind_speed,
        windDirection = current.wind_deg,
        cloudiness = current.clouds,
        visibility = current.visibility,
        uvi = current.uvi,
        sunrise = current.sunrise,
        sunset = current.sunset
    )
}
