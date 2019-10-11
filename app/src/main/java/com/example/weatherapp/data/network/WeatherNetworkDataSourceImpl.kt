package com.example.weatherapp.data.network

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.weatherapp.data.network.response.CurrentWeatherResponse
import com.example.weatherapp.internal.NoConnectivityException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class WeatherNetworkDataSourceImpl(
    private val apixuApiService: ApixuApiService
) : WeatherNetworkDataSource {
    private val _downloadedCurrentWeather = MutableLiveData<CurrentWeatherResponse>()
    override val downloadedCurrentWeather: LiveData<CurrentWeatherResponse>
        get() = _downloadedCurrentWeather

    override suspend fun fetchCurrentWeather(location: String, metric: String) {
        try {
            withContext(Dispatchers.IO) {
                val fetchedCurrentWeather = apixuApiService.getCurrentWeather(location, metric)
                _downloadedCurrentWeather.postValue(fetchedCurrentWeather)
            }
        } catch (e: NoConnectivityException) {
            Log.e("Connectivity", e.message)
        }
    }
}