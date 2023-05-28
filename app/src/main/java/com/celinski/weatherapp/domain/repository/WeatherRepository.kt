package com.celinski.weatherapp.domain.repository

import com.celinski.weatherapp.domain.util.Helper
import com.celinski.weatherapp.domain.entity.WeatherMultipleModel

interface WeatherRepository {
    suspend fun getWeatherMultipleModel(lat: Double, long: Double): Helper<WeatherMultipleModel>
}