package com.example.weatherapp.data.repository

import androidx.lifecycle.LiveData
import com.example.weatherapp.data.database.entity.CurrentWeatherEntry

interface ForecastRepository {
    val currentWeather: LiveData<CurrentWeatherEntry>
    suspend fun initWeatherData(location: String, metric: String)
}