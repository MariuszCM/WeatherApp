package com.celinski.weatherapp.frontend

import com.celinski.weatherapp.domain.entity.WeatherMultipleModel

data class WeatherState(
    val weatherMultipleModel: WeatherMultipleModel? = null,
    val city: String? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)
