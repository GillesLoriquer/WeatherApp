package com.example.weatherapp.ui.weather.current

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.data.database.entity.CurrentWeatherEntry
import com.example.weatherapp.data.repository.ForecastRepository
import kotlinx.coroutines.launch

class CurrentWeatherViewModel(
    private val forecastRepository: ForecastRepository
) : ViewModel() {

    var currentWeather: LiveData<CurrentWeatherEntry> = forecastRepository.currentWeather

    init {
        viewModelScope.launch {
            forecastRepository.initWeatherData()
        }
    }
}
