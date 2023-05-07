package com.celinski.weatherapp.domain.repository

import com.celinski.weatherapp.domain.util.Resource
import com.celinski.weatherapp.domain.weather.WeatherModel
import com.celinski.weatherapp.domain.weather.WeatherMultipleModel

interface WeatherRepository {
    suspend fun getWeatherMultipleModel(lat: Double, long: Double): Resource<WeatherMultipleModel>
}