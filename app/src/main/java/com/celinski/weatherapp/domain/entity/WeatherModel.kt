package com.celinski.weatherapp.domain.entity

import java.time.LocalDateTime

data class WeatherModel(
    val time: LocalDateTime,
    val temperatureCelsius: Double,
    val pressure: Double,
    val windSpeed: Double,
    val humidity: Double,
    val weatherType: WeatherType,
)
