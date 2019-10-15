package com.example.weatherapp.data.network.response

import com.example.weatherapp.data.database.entity.CurrentWeatherEntry
import com.example.weatherapp.data.database.entity.Request
import com.example.weatherapp.data.database.entity.WeatherLocation
import com.squareup.moshi.Json

data class CurrentWeatherResponse(
    val request: Request,
    val location: WeatherLocation,
    @Json(name = "current")
    val currentWeatherEntry: CurrentWeatherEntry
)