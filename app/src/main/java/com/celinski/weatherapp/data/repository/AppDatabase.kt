package com.celinski.weatherapp.data.repository

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.celinski.weatherapp.domain.dao.WeatherModelDao
import com.celinski.weatherapp.domain.entity.WeatherModel
import com.celinski.weatherapp.domain.entity.WeatherMultipleModel
import com.celinski.weatherapp.domain.entity.WeatherType

@Database(entities = [WeatherMultipleModel::class, WeatherModel::class], version = 3)
@TypeConverters(LocalDateTimeConverter::class, WeatherTypeConverter::class, WeatherModelMapConverter::class, WeatherModelConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun weatherDao(): WeatherModelDao
}