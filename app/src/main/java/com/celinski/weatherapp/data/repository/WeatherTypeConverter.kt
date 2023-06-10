package com.celinski.weatherapp.data.repository

import androidx.room.TypeConverter
import com.celinski.weatherapp.domain.entity.WeatherType

class WeatherTypeConverter {
    @TypeConverter
    fun fromWeatherType(weatherType: WeatherType): String {
        return weatherType.javaClass.simpleName
    }

    @TypeConverter
    fun toWeatherType(weatherTypeString: String): WeatherType {
        return try {
            val code = weatherTypeString.toInt()
            WeatherType.fromWMO(code)
        } catch (e: NumberFormatException) {
            WeatherType.ClearSky
        }
    }
}