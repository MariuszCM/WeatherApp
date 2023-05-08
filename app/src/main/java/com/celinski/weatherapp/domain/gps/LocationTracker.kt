package com.celinski.weatherapp.domain.gps

import android.location.Location

interface LocationTracker {
    suspend fun gerCurrentLocation(): Location?
}