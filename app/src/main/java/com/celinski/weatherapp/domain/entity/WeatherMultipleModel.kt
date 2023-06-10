package com.celinski.weatherapp.domain.entity

import androidx.room.Entity
import androidx.room.TypeConverters
import com.celinski.weatherapp.data.repository.WeatherModelMapConverter

@Entity(tableName = "weather_multiple_model", primaryKeys = ["city"])
data class WeatherMultipleModel(
    @TypeConverters(WeatherModelMapConverter::class)
    val weatherModelPerDay: Map<Int, List<WeatherModel>>,
    val currentWeatherModel: WeatherModel?,
    val city: String
)
