package com.celinski.weatherapp.domain.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.celinski.weatherapp.domain.entity.WeatherModel
import com.celinski.weatherapp.domain.entity.WeatherMultipleModel

@Dao
interface WeatherModelDao {
//    @Insert
//    fun insert(weather: WeatherModel?)
//
//    @Update
//    fun update(weather: WeatherModel)
//
//    @Query("DELETE FROM weather WHERE city = :city")
//    fun deleteWeatherByCity(city: String)
//
//    @Query("SELECT * FROM weather")
//    fun getAllWeather(): List<WeatherModel>
//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    suspend fun insert(weatherMultipleModelEntity: WeatherMultipleModel)
//
//    @Query("SELECT * FROM weather_multiple_model")
//    fun getAll(): LiveData<List<WeatherMultipleModel>>
//
//    @Query("SELECT * FROM weather_multiple_model WHERE city = :city")
//    fun getByCity(city: String): LiveData<WeatherMultipleModel?>
//
//    @Delete
//    suspend fun delete(weatherMultipleModelEntity: WeatherMultipleModel)



    //

    @Query("SELECT * FROM weather_multiple_model")
    fun getAll(): List<WeatherMultipleModel>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(weatherMultipleModel: WeatherMultipleModel)

    @Delete
    fun delete(weatherMultipleModel: WeatherMultipleModel)
}