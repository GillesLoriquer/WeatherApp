package com.example.weatherapp.data.repository

import androidx.lifecycle.LiveData
import com.example.weatherapp.data.database.CurrentWeatherDao
import com.example.weatherapp.data.database.WeatherLocationDao
import com.example.weatherapp.data.database.entity.CurrentWeatherEntry
import com.example.weatherapp.data.database.entity.WeatherLocation
import com.example.weatherapp.data.network.WeatherNetworkDataSource
import com.example.weatherapp.data.network.response.CurrentWeatherResponse
import com.example.weatherapp.data.provider.LocationProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.threeten.bp.ZonedDateTime

class ForecastRepositoryImpl(
    private val currentWeatherDao: CurrentWeatherDao,
    private val currentLocationDao: WeatherLocationDao,
    private val weatherNetworkDataSource: WeatherNetworkDataSource,
    private val locationProvider: LocationProvider
) : ForecastRepository {

    override val currentWeather: LiveData<CurrentWeatherEntry> = currentWeatherDao.getCurrentWeather()

    override val weatherLocation: LiveData<WeatherLocation> = currentLocationDao.getWeatherLocation()

    init {
        // on utilise observeForever car un repository n'a pas de LifeCycle.
        weatherNetworkDataSource.downloadedCurrentWeather.observeForever {
            // persist
            persistFetchedCurrentWeather(it)
        }
    }

    private fun persistFetchedCurrentWeather(fetchedWeather: CurrentWeatherResponse) {
        // à nouveau, on peut utiliser le GlobalScope car le repository n'est pas lié à un LifeCycle comme dans une
        // activité ou un fragment
        GlobalScope.launch(Dispatchers.IO) {
            currentWeatherDao.upsert(fetchedWeather.currentWeatherEntry)
            currentLocationDao.upsert(fetchedWeather.location)
        }
    }

    override suspend fun initWeatherData(metric: String) {
        val weatherLocationValue = weatherLocation.value

        if (weatherLocationValue == null || locationProvider.hasLocationChanged(weatherLocationValue)) {
            fetchCurrentWeather(metric)
            return
        }

        if (isFetchCurrentNeeded(weatherLocationValue.zoneDateTime)) {
            fetchCurrentWeather(metric)
        }
    }

    private suspend fun fetchCurrentWeather(metric: String) {
        weatherNetworkDataSource.fetchCurrentWeather(locationProvider.getPreferredLocationString(), metric)
    }

    private fun isFetchCurrentNeeded(lastFetchedTime: ZonedDateTime): Boolean {
        val thirtyMinutesAgo = ZonedDateTime.now().minusMinutes(30)
        return lastFetchedTime.isBefore(thirtyMinutesAgo)
    }
}