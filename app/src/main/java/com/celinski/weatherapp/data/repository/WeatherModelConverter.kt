package com.celinski.weatherapp.data.repository

import androidx.room.TypeConverter
import com.celinski.weatherapp.domain.entity.WeatherModel
import com.celinski.weatherapp.domain.entity.WeatherType
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.InstanceCreator
import java.lang.reflect.Type

class WeatherModelConverter {
//    @TypeConverter
//    fun fromWeatherModel(value: WeatherModel): String {
//        val gson = Gson()
//        return gson.toJson(value)
//    }
//
//    @TypeConverter
//    fun toWeatherModel(value: String): WeatherModel {
//        val gson = Gson()
//        val type = object : TypeToken<WeatherModel>() {}.type
//        return gson.fromJson(value, type)
//    }

    private val gson: Gson = GsonBuilder()
        .registerTypeAdapter(WeatherType::class.java, WeatherTypeInstanceCreator())
        .create()

    @TypeConverter
    fun fromWeatherModel(value: WeatherModel): String {
        return gson.toJson(value)
    }

    @TypeConverter
    fun toWeatherModel(value: String): WeatherModel {
        return gson.fromJson(value, WeatherModel::class.java)
    }

    private class WeatherTypeInstanceCreator : InstanceCreator<WeatherType> {
        override fun createInstance(type: Type?): WeatherType {
            return WeatherType.ClearSky
        }
    }
}