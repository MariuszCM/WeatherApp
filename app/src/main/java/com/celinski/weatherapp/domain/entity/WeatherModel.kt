package com.celinski.weatherapp.domain.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import org.jetbrains.annotations.NotNull
import java.time.LocalDateTime

@Entity(tableName = "weather_model")
data class WeatherModel(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val time: LocalDateTime,
    val temperatureCelsius: Double,
    val pressure: Double,
    val windSpeed: Double,
    val humidity: Double,
    val weatherType: WeatherType,
)
