package com.celinski.weatherapp.domain.weather

data class WeatherMultipleModel(
    val weatherModelPerDay: Map<Int, List<WeatherModel>>,
    val currentWeatherModel: WeatherModel?
)
