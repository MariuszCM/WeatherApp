package com.celinski.weatherapp.data.repository

import com.celinski.weatherapp.data.api.WeatherApi
import com.celinski.weatherapp.data.mappers.toWeatherInfo
import com.celinski.weatherapp.domain.dao.WeatherModelDao
import com.celinski.weatherapp.domain.repository.WeatherRepository
import com.celinski.weatherapp.domain.util.Helper
import com.celinski.weatherapp.domain.entity.WeatherMultipleModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private  val api: WeatherApi,
    private val weatherDao: WeatherModelDao
): WeatherRepository {
    override suspend fun getWeatherMultipleModel(lat: Double, long: Double, city: String): Helper<WeatherMultipleModel> {
        return try {
            val weatherResponse = api.getWeatherFromApi(lat = lat, long = long).toWeatherInfo(city)
            GlobalScope.launch(Dispatchers.IO) {
                weatherDao.insert(weatherResponse)
                weatherDao.getAll()
                val y = 6
            }

            Helper.Success(data = weatherResponse)
        } catch (e: Exception) {
            e.printStackTrace()
            Helper.Error(e.message ?: "Unsupported error occurred")
        }
    }
}