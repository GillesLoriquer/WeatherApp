package com.example.weatherapp.ui.weather.current

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.weatherapp.data.provider.UnitProvider
import com.example.weatherapp.data.repository.ForecastRepository

class CurrentWeatherViewModelFactory(
    private val forecastRepository: ForecastRepository,
    private val unitProvider: UnitProvider
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CurrentWeatherViewModel::class.java)) {
            return CurrentWeatherViewModel(forecastRepository, unitProvider) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}