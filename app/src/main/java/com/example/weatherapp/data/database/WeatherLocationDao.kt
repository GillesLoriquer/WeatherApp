package com.example.weatherapp.data.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.weatherapp.data.database.entity.CURRENT_LOCATION_ID
import com.example.weatherapp.data.database.entity.WeatherLocation

@Dao
interface WeatherLocationDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun upsert(weatherLocation: WeatherLocation)

    @Query("SELECT * FROM 'current_location' WHERE id = $CURRENT_LOCATION_ID")
    fun getWeatherLocation(): LiveData<WeatherLocation>
}