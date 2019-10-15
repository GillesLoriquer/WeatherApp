package com.example.weatherapp.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.weatherapp.data.database.entity.CurrentWeatherEntry
import com.example.weatherapp.data.database.entity.WeatherLocation

@TypeConverters(Converters::class)
@Database(entities = [CurrentWeatherEntry::class, WeatherLocation::class], version = 1, exportSchema = false)
abstract class ForecastDatabase : RoomDatabase() {

    abstract fun currentWeatherDao(): CurrentWeatherDao
    abstract fun weatherLocationDao(): WeatherLocationDao

    companion object {

        @Volatile
        private var instance: ForecastDatabase? = null

        operator fun invoke(context: Context): ForecastDatabase = instance ?: synchronized(this) {
            instance ?: buildDatabase(context).also { instance = it }
        }

        private fun buildDatabase(context: Context) = Room.databaseBuilder(
            context.applicationContext,
            ForecastDatabase::class.java,
            "forecasts"
        )
            .build()
    }
}