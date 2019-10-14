package com.example.weatherapp.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json

const val CURRENT_WEATHER_ID = 0

@Entity(tableName = "current_weather")
data class CurrentWeatherEntry(
    val temperature: Double,
    @Json(name = "weather_icons")
    val weatherIcons: List<String>,
    @Json(name = "weather_descriptions")
    val weatherDescriptions: List<String>,
    @Json(name = "wind_speed")
    val windSpeed: Double,
    @Json(name = "wind_dir")
    val windDir: String,
    val pressure: Double,
    val precip: Double,
    val humidity: Double,
    val cloudcover: Int,
    val feelslike: Double,
    @Json(name = "uv_index")
    val uvIndex: Int,
    val visibility: Int,
    @Json(name = "is_day")
    val isDay: String
) {
    // There will be only one instance of current weather, so id can be a constant and should not be autogenerated
    @PrimaryKey(autoGenerate = false)
    var id: Int = CURRENT_WEATHER_ID
}