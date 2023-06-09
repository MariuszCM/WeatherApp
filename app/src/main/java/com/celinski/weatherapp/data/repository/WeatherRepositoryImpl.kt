package com.celinski.weatherapp.data.repository

import com.celinski.weatherapp.data.api.WeatherApi
import com.celinski.weatherapp.data.mappers.toWeatherInfo
import com.celinski.weatherapp.domain.dao.WeatherModelDao
import com.celinski.weatherapp.domain.entity.WeatherMultipleModel
import com.celinski.weatherapp.domain.repository.WeatherRepository
import com.celinski.weatherapp.domain.util.Helper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class WeatherRepositoryImpl @Inject constructor(
    private val api: WeatherApi,
    private val weatherDao: WeatherModelDao
) : WeatherRepository {
    override suspend fun getWeatherMultipleModel(
        lat: Double,
        long: Double,
        city: String
    ): Helper<WeatherMultipleModel> {
        return try {
            val weatherResponse = getWeatherDataFromRepo(lat, long, city)

            Helper.Success(data = weatherResponse)
        } catch (e: Exception) {
            e.printStackTrace()
            Helper.Error(e.message ?: "Unsupported error occurred")
        }
    }

    private suspend fun getWeatherDataFromRepo(
        lat: Double,
        long: Double,
        city: String
    ): WeatherMultipleModel = suspendCoroutine { continuation ->
        GlobalScope.launch(Dispatchers.IO) {
            val cacheData = weatherDao.getAll()
            val updatedCacheData = cleanUpCache(cacheData, city)
            if (updatedCacheData.isEmpty()) {
                val apiResponse = api.getWeatherFromApi(lat = lat, long = long).toWeatherInfo(city)
                weatherDao.insert(apiResponse)
                continuation.resume(apiResponse)
            } else {
                continuation.resume(updatedCacheData[0])
            }
        }
    }

    private fun cleanUpCache(
        cacheData: List<WeatherMultipleModel>,
        city: String
    ): List<WeatherMultipleModel> {
        cacheData.forEach { weatherItem ->
            if (weatherItem.city != city || weatherItem.cacheTime.isBefore(
                    LocalDateTime.now().minusHours(4)
                )
            ) {
                weatherDao.delete(weatherItem)
            }
        }
        return weatherDao.getAll()
    }
}