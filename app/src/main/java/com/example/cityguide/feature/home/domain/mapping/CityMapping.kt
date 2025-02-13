package com.example.cityguide.feature.home.domain.mapping

import com.example.cityguide.feature.home.data.model.CityResult
import com.example.cityguide.feature.home.domain.model.City


fun CityResult.toDomainModel(): City {
    return City(
        id = id,
        name = name,
        country = country
    )
}