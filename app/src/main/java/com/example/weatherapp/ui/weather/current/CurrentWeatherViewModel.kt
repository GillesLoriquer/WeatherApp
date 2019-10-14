package com.example.weatherapp.ui.weather.current

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.data.database.entity.CurrentWeatherEntry
import com.example.weatherapp.data.provider.UnitProvider
import com.example.weatherapp.data.repository.ForecastRepository
import kotlinx.coroutines.launch

class CurrentWeatherViewModel(
    private val forecastRepository: ForecastRepository,
    unitProvider: UnitProvider
) : ViewModel() {

    val currentWeather: LiveData<CurrentWeatherEntry> = forecastRepository.currentWeather

    private val unitSystem = unitProvider.getUnitSystem()

    init {
        viewModelScope.launch {
            forecastRepository.initWeatherData("La Chapelle sur Erdre", unitSystem.code)
        }
    }
}
