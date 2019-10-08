package com.example.weatherapp.data.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.weatherapp.data.database.entity.CURRENT_WEATHER_ID
import com.example.weatherapp.data.database.entity.CurrentWeatherEntry

@Dao
interface CurrentWeatherDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun upsert(weatherEntry: CurrentWeatherEntry)

    @Query("SELECT * FROM 'current_weather' WHERE id = $CURRENT_WEATHER_ID")
    fun getCurrentWeather(): LiveData<CurrentWeatherEntry>
}