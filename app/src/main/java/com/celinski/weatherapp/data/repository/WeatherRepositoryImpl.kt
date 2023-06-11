package com.celinski.weatherapp.data.repository

import com.celinski.weatherapp.data.api.WeatherApi
import com.celinski.weatherapp.data.mappers.toWeatherInfo
import com.celinski.weatherapp.domain.dao.WeatherModelDao
import com.celinski.weatherapp.domain.repository.WeatherRepository
import com.celinski.weatherapp.domain.util.Helper
import com.celinski.weatherapp.domain.entity.WeatherMultipleModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.lang.Exception
import java.time.LocalDateTime
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private  val api: WeatherApi,
    private val weatherDao: WeatherModelDao
): WeatherRepository {
    override suspend fun getWeatherMultipleModel(lat: Double, long: Double, city: String): Helper<WeatherMultipleModel> {
        return try {
            val weatherResponse = getWeatherDataFromRepo(lat, long, city)

            Helper.Success(data = weatherResponse)
        } catch (e: Exception) {
            e.printStackTrace()
            Helper.Error(e.message ?: "Unsupported error occurred")
        }
    }

    private suspend fun getWeatherDataFromRepo(lat: Double, long: Double, city: String): WeatherMultipleModel {
        val cacheData = withContext(Dispatchers.IO) {
            weatherDao.getAll()
        }
        cleanUpCache(cacheData, city)
        if (cacheData.isEmpty()) {
            val apiResponse = api.getWeatherFromApi(lat = lat, long = long).toWeatherInfo(city)
            withContext(Dispatchers.IO) {
                weatherDao.insert(apiResponse)
            }
            return apiResponse
        } else {
            return cacheData[0]
        }
    }

    private suspend fun cleanUpCache(
        cacheData: List<WeatherMultipleModel>,
        city: String
    ) {
        cacheData.forEach { weatherItem ->
            if (weatherItem.cacheTime.isBefore(
                    LocalDateTime.now().minusHours(4)
                ) || weatherItem.city != city
            ) {
                withContext(Dispatchers.IO) {
                    weatherDao.delete(weatherItem)
                }
            }
        }
    }
}