package com.example.weatherapp.data.database

import androidx.room.TypeConverter
import com.squareup.moshi.Moshi


class Converters {
    @TypeConverter
    fun fromStringListToJson(value: List<String>): String {
        val moshi = Moshi.Builder().build()
        val jsonAdapter = moshi.adapter<List<String>>(List::class.java)

        return jsonAdapter.toJson(value)
    }

    @TypeConverter
    fun fromJsonToStringList(value: String): List<String> {
        val moshi = Moshi.Builder().build()
        val jsonAdapter = moshi.adapter<List<String>>(List::class.java)

        return jsonAdapter.fromJson(value) ?: listOf()
    }
}