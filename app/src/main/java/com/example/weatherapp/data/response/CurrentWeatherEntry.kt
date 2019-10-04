package com.example.weatherapp.data.response


import com.squareup.moshi.Json

data class CurrentWeatherEntry(
    @Json(name = "observation_time")
    val observationTime: String,
    val temperature: Int,
    @Json(name = "weather_code")
    val weatherCode: Int,
    @Json(name = "weather_icons")
    val weatherIcons: List<String>,
    @Json(name = "weather_descriptions")
    val weatherDescriptions: List<String>,
    @Json(name = "wind_speed")
    val windSpeed: Int,
    @Json(name = "wind_degree")
    val windDegree: Int,
    @Json(name = "wind_dir")
    val windDir: String,
    val pressure: Int,
    val precip: Int,
    val humidity: Int,
    val cloudcover: Int,
    val feelslike: Int,
    @Json(name = "uv_index")
    val uvIndex: Int,
    val visibility: Int,
    @Json(name = "is_day")
    val isDay: String
)