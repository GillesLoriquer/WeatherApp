package com.example.weatherapp.data.provider

import com.example.weatherapp.internal.UnitSystem

interface UnitProvider {
    fun getUnitSystem(): UnitSystem
}