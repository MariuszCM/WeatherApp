package com.celinski.weatherapp.data.repository

import androidx.room.TypeConverter
import com.celinski.weatherapp.domain.entity.WeatherModel
import com.celinski.weatherapp.domain.entity.WeatherType
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.InstanceCreator
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

class WeatherModelMapConverter {

    private val gson: Gson = GsonBuilder()
        .registerTypeAdapter(WeatherType::class.java, WeatherTypeInstanceCreator())
        .create()

    @TypeConverter
    fun fromWeatherModelMap(value: Map<Int, List<WeatherModel>>): String {
        val type: Type = object : TypeToken<Map<Int, List<WeatherModel>>>() {}.type
        return gson.toJson(value, type)
    }

    @TypeConverter
    fun toWeatherModelMap(value: String): Map<Int, List<WeatherModel>> {
        val type: Type = object : TypeToken<Map<Int, List<WeatherModel>>>() {}.type
        return gson.fromJson(value, type)
    }

    private class WeatherTypeInstanceCreator : InstanceCreator<WeatherType> {
        override fun createInstance(type: Type?): WeatherType {
            return WeatherType.ClearSky
        }
    }
}