package com.example.weatherapp.data.provider

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager

abstract class PreferencesProvider(context: Context) {

    private val appContext = requireNotNull(context.applicationContext)

    protected val preferences: SharedPreferences
        get() {
            return PreferenceManager.getDefaultSharedPreferences(appContext)
        }
}