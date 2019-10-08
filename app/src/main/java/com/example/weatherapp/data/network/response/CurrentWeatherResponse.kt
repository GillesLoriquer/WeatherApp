package com.example.weatherapp.data.network.response

import com.example.weatherapp.data.database.entity.CurrentWeatherEntry
import com.example.weatherapp.data.database.entity.Location
import com.example.weatherapp.data.database.entity.Request
import com.squareup.moshi.Json

data class CurrentWeatherResponse(
    val request: Request,
    val location: Location,
    @Json(name = "current")
    val currentWeatherEntry: CurrentWeatherEntry
)