package com.celinski.weatherapp.domain.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.celinski.weatherapp.domain.entity.WeatherMultipleModel

@Dao
interface WeatherModelDao {
    @Query("SELECT * FROM weather_multiple_model")
    fun getAll(): List<WeatherMultipleModel>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(weatherMultipleModel: WeatherMultipleModel)

    @Delete
    fun delete(weatherMultipleModel: WeatherMultipleModel)
}