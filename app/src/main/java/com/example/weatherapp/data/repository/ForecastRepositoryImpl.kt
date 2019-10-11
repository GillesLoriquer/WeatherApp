package com.example.weatherapp.data.repository

import androidx.lifecycle.LiveData
import com.example.weatherapp.data.database.CurrentWeatherDao
import com.example.weatherapp.data.database.entity.CurrentWeatherEntry
import com.example.weatherapp.data.network.WeatherNetworkDataSource
import com.example.weatherapp.data.network.response.CurrentWeatherResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.threeten.bp.ZonedDateTime

class ForecastRepositoryImpl(
    private val currentWeatherDao: CurrentWeatherDao,
    private val weatherNetworkDataSource: WeatherNetworkDataSource
) : ForecastRepository {

    override val currentWeather: LiveData<CurrentWeatherEntry> = currentWeatherDao.getCurrentWeather()

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
        }
    }

    override suspend fun initWeatherData() {
        if (isFetchCurrentNeeded(ZonedDateTime.now().minusHours(1)))
            fetchCurrentWeather()
    }

    private suspend fun fetchCurrentWeather() {
        weatherNetworkDataSource.fetchCurrentWeather("Nantes")
    }

    private fun isFetchCurrentNeeded(lastFetchedTime: ZonedDateTime): Boolean {
        val thirtyMinutesAgo = ZonedDateTime.now().minusMinutes(30)
        return lastFetchedTime.isBefore(thirtyMinutesAgo)
    }
}