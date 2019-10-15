package com.example.weatherapp.data.repository

import androidx.lifecycle.LiveData
import com.example.weatherapp.data.database.entity.CurrentWeatherEntry
import com.example.weatherapp.data.database.entity.WeatherLocation

interface ForecastRepository {
    val currentWeather: LiveData<CurrentWeatherEntry>
    val weatherLocation: LiveData<WeatherLocation>
    suspend fun initWeatherData(metric: String)
}