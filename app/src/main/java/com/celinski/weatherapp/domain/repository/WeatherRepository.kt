package com.celinski.weatherapp.domain.repository

import com.celinski.weatherapp.domain.entity.WeatherMultipleModel
import com.celinski.weatherapp.domain.util.Helper

interface WeatherRepository {
    suspend fun getWeatherMultipleModel(
        lat: Double,
        long: Double,
        city: String
    ): Helper<WeatherMultipleModel>
}