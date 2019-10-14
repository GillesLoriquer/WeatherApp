package com.example.weatherapp.data.provider

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import com.example.weatherapp.internal.UnitSystem

// key value used to save current unit system in SharedPreferences
const val UNIT_SYSTEM = "UNIT_SYSTEM"

class UnitProviderImpl(context: Context) : UnitProvider {

    private val appContext = context.applicationContext

    private val preferences: SharedPreferences
        get() = PreferenceManager.getDefaultSharedPreferences(appContext)

    override fun getUnitSystem(): UnitSystem {
        val selectedUnitSystem = preferences.getString(UNIT_SYSTEM, UnitSystem.METRIC.system)
        return UnitSystem.valueOf(selectedUnitSystem!!)
    }
}