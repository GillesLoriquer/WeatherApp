package com.example.weatherapp.data.response

import com.squareup.moshi.Json

data class CurrentWeatherResponse(
    val request: Request,
    val location: Location,
    @Json(name = "current")
    val currentWeatherEntry: CurrentWeatherEntry
)