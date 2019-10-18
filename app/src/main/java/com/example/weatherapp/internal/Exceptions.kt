package com.example.weatherapp.internal

import java.io.IOException

class NoConnectivityException(errorMessage: String) : IOException(errorMessage)
class LocationPermissionNotGrantedException : Exception()