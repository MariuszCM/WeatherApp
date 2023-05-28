package com.celinski.weatherapp.domain.entity

data class WeatherMultipleModel(
    val weatherModelPerDay: Map<Int, List<WeatherModel>>,
    val currentWeatherModel: WeatherModel?
)
