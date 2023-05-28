package com.celinski.weatherapp.domain.gps

import android.location.Location

interface GpsTracker {
    suspend fun getCurrentLocation(): Location?
}